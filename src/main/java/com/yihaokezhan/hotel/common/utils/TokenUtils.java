package com.yihaokezhan.hotel.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import com.yihaokezhan.hotel.common.redis.TokenRedisService;
import com.yihaokezhan.hotel.model.TokenUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import lombok.extern.slf4j.Slf4j;


/**
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Component
@Slf4j
public class TokenUtils {
    /**
     * 12小时后过期
     */
    private final int EXPIRE = 3600 * 12;

    private final int TOKEN_BIT_COUNT = 64;

    @Autowired
    private TokenRedisService tokenRedis;

    public int getDefaultExpire() {
        return EXPIRE;
    }

    public String createToken(TokenUser tokenUser) {
        tokenUser.setToken(RandomStringUtils.randomAlphanumeric(TOKEN_BIT_COUNT));
        if (tokenUser.getExpiredAt() == null) {
            tokenUser.setExpiredAt(
                    LocalDateTime.now().plusHours(EXPIRE).toEpochSecond(ZoneOffset.UTC) * 1000);
        }
        String key = tokenCacheKey(tokenUser.getToken());
        long expire = (tokenUser.getExpiredAt() - (System.currentTimeMillis())) / 1000;
        tokenRedis.set(key, tokenUser, expire);
        tokenRedis.add(userTokensCacheKey(tokenUser), tokenUser.getToken(), expire);
        return tokenUser.getToken();
    }

    public void expireToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return;
        }
        TokenUser tokenUser = getUserByToken(token);
        removeAllTokens(tokenUser);
        tokenRedis.delete(tokenCacheKey(token));
    }

    public void expireToken(Map<String, Integer> accountMap) {
        if (accountMap == null || accountMap.isEmpty()) {
            return;
        }
        // 过期指定账号的Token
        for (String uuid : accountMap.keySet()) {
            removeAllTokens(accountMap.get(uuid), uuid);
        }
    }

    public TokenUser getUserByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        TokenUser tu = tokenRedis.get(tokenCacheKey(token), TokenUser.class);
        if (tu == null) {
            log.error("redis 中未查询到 {}", tokenCacheKey(token));
        }
        log.info("get tokenUser by token {}", tu != null ? tu.toString() : null);
        return tu;
    }

    public String getUuidByToken(String token) {
        TokenUser tokenUser = getUserByToken(token);
        if (tokenUser == null) {
            log.error("token {} has no tokenUser", token);
            return null;
        }
        return tokenUser.getUuid();
    }

    public boolean isValidToken(String token) {
        return getUserByToken(token) != null;
    }

    public String getTokenFromReq(HttpServletRequest request) {
        // 从header中获取token
        String token = request.getHeader(Constant.ACCESS_TOKEN_HEADER);
        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(Constant.ACCESS_TOKEN_HEADER);
        }
        // token为空
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }

    public String getTokenFromReq(NativeWebRequest request) {
        // 从header中获取token
        String token = request.getHeader(Constant.ACCESS_TOKEN_HEADER);
        log.info("get token from header {}", token);
        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(Constant.ACCESS_TOKEN_HEADER);
            log.info("get token from params {}", token);
        }
        // token为空
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }

    public String getTokenFromReq(ServerHttpRequest request) {
        // 从header中获取token
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(Constant.ACCESS_TOKEN_HEADER);
        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getQueryParams().getFirst(Constant.ACCESS_TOKEN_HEADER);
        }
        // token为空
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }

    // 删除用户所有的token
    private void removeAllTokens(TokenUser tokenUser) {
        if (tokenUser == null) {
            return;
        }
        removeAllTokens(tokenUser.getAccountType(), tokenUser.getUuid());
    }

    private void removeAllTokens(String tk) {
        Set<Object> tokenSet = tokenRedis.members(tk);
        tokenRedis.delete(tk);
        if (tokenSet == null || tokenSet.isEmpty()) {
            return;
        }
        List<String> tokens =
                tokenSet.stream().map(this::tokenCacheKey).collect(Collectors.toList());
        tokenRedis.delete(tokens);
    }

    // 删除用户所有的token
    private void removeAllTokens(Integer accountType, String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return;
        }
        String tk = userTokensCacheKey(accountType, uuid);
        removeAllTokens(tk);
    }

    private String tokenCacheKey(Object token) {
        return Constant.TOKEN_KEY_PREFIX + token.toString();
    }

    private String userTokensCacheKey(TokenUser tokenUser) {
        return userTokensCacheKey(tokenUser.getAccountType(), tokenUser.getUuid());
    }

    private String userTokensCacheKey(Integer accountType, String uuid) {
        return Constant.USER_TOKENS_KEY_PREFIX + accountType.toString() + ":" + uuid;
    }
}
