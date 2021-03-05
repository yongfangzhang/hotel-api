package com.yihaokezhan.hotel.common.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.redis.CacheRedisService;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.TokenUtils;
import com.yihaokezhan.hotel.model.TokenUser;
import com.yihaokezhan.hotel.module.entity.AccountRole;
import com.yihaokezhan.hotel.module.service.IAccountRoleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Component
@Slf4j
public class ShiroUtils {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private IAccountRoleService accountRoleService;

    @Autowired
    private CacheRedisService cacheRedisService;

    public ShiroSet getAccountShiroSet(TokenUser tokenUser) {
        ShiroSet shiroSet = cacheRedisService.getShiroSet(tokenUser.getUuid());
        if (shiroSet != null) {
            return shiroSet;
        }

        shiroSet = new ShiroSet();

        List<AccountRole> accountRoles =
                accountRoleService.mList(M.m().put("accountUuid", tokenUser.getUuid()));
        if (CollectionUtils.isNotEmpty(accountRoles)) {
            HashSet<String> perms = new HashSet<>();
            HashSet<String> roles = new HashSet<>();
            accountRoles.stream().forEach(ar -> {
                if (ar.getRole() != null) {
                    roles.add(ar.getRole().getCode());
                    perms.addAll(ar.getRole().getRoutes().stream()
                            .flatMap(r -> r.getPermissions().stream()).collect(Collectors.toSet()));
                }
            });
            shiroSet.setPermissions(perms);
            shiroSet.setRoles(roles);
        }
        cacheRedisService.cacheShiroSet(tokenUser.getUuid(), shiroSet);
        return shiroSet;
    }

    public static TokenUser getTokenUser() {
        try {
            TokenUser tokenUser = (TokenUser) SecurityUtils.getSubject().getPrincipal();
            log.info("shiro get tokenUser {}", tokenUser.toString());
            return tokenUser;
        } catch (Exception e) {
            log.error("shiro 获取用户失败: {}", e.getMessage(), e);
            return null;
        }
    }

    // private String getPermKey(String uuid) {
    // return Constant.CACHE_PREFIX_ACCOUNT_PERMS + uuid;
    // }

    // private String getRoleKey(String uuid) {
    // return Constant.CACHE_PREFIX_ACCOUNT_ROLES + uuid;
    // }

    // public void updatePermCache(String uuid, List<String> perms) {
    // redisService.permanentSet(getPermKey(uuid), perms);
    // }

    // public void updateRoleCache(String uuid, List<String> roles) {
    // redisService.permanentSet(getRoleKey(uuid), roles);
    // }

    // public void clearPermCache(String uuid) {
    // redisService.delete(getPermKey(uuid));
    // redisService.delete(getRoleKey(uuid));
    // }

    // public void clearPermCache() {
    // redisService.deleteBatch(Constant.CACHE_PREFIX_ACCOUNT_PERMS);
    // redisService.deleteBatch(Constant.CACHE_PREFIX_ACCOUNT_ROLES);
    // }

    public boolean login(String uuid, String token) {
        // 生成无状态Token
        StatelessToken statelessToken = new StatelessToken(uuid, token);
        try {
            log.info("shiro login, uuid {}, token {}", uuid, token);
            // 委托给Realm进行登录
            SecurityUtils.getSubject().login(statelessToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean logout(TokenUser tokenUser) {
        try {
            if (tokenUser != null) {
                tokenUtils.expireToken(tokenUser.getToken());
            }
            SecurityUtils.getSubject().logout();
            return true;
        } catch (Exception e) {
            log.error("Shiro 退出 异常: {}", e.getMessage());
            return false;
        }
    }

    public static void logout() {
        // 执行后退出
        try {
            SecurityUtils.getSubject().logout();
            log.info("Shiro 退出");
        } catch (Exception e) {
            log.error("Shiro 退出 异常: {}", e.getMessage());
        }
    }
}
