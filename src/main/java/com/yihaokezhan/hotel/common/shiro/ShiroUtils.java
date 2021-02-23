package com.yihaokezhan.hotel.common.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.yihaokezhan.hotel.common.redis.RedisService;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.module.entity.User;
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
    private RedisService redisUtils;

    public Set<String> getPermsByUuid(User user) {

        Set<String> sets = new HashSet<String>();
        String uuid = user.getUuid();
        List<String> perms = redisUtils.getList(getPermKey(uuid), String.class);
        if (perms == null) {
            return sets;
        }
        return new HashSet<>(perms);
    }

    public static User getUser() {
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            log.info("shiro get user {}", user.toString());
            return user;
        } catch (Exception e) {
            log.error("shiro 获取用户失败: {}", e.getMessage(), e);
            return null;
        }
    }

    private String getPermKey(String uuid) {
        return Constant.USER_PERMS_PREFIX + uuid;
    }

    public void updatePermCache(String uuid, List<String> perms) {
        redisUtils.permanentSet(getPermKey(uuid), perms);
    }

    public void clearPermCache(String uuid) {
        redisUtils.delete(getPermKey(uuid));
    }

    public boolean login(String openId) {
        // 生成无状态Token
        StatelessToken statelessToken = new StatelessToken(openId);
        try {
            log.info("shiro login, openId {}", openId);
            // 委托给Realm进行登录
            SecurityUtils.getSubject().login(statelessToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public static void logout() {
        try {
            SecurityUtils.getSubject().logout();
        } catch (Exception e) {
            log.error("Shiro 退出 异常: {}", e.getMessage());
        }
    }
}
