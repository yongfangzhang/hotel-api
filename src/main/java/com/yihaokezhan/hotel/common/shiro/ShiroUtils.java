package com.yihaokezhan.hotel.common.shiro;

import java.util.HashSet;
import java.util.List;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
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

    public ShiroSet getUserShiroSet(User user) {
        ShiroSet shiroSet = new ShiroSet();
        String uuid = user.getUuid();
        List<String> perms = redisUtils.getList(getPermKey(uuid), String.class);
        List<String> roles = redisUtils.getList(getRoleKey(uuid), String.class);
        if (CollectionUtils.isNotEmpty(perms)) {
            shiroSet.setPermissions(new HashSet<>(perms));
        }
        if (CollectionUtils.isNotEmpty(roles)) {
            shiroSet.setRoles(new HashSet<>(perms));
        }
        return shiroSet;
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

    private String getRoleKey(String uuid) {
        return Constant.USER_ROLES_PREFIX + uuid;
    }

    public void updatePermCache(String uuid, List<String> perms) {
        redisUtils.permanentSet(getPermKey(uuid), perms);
    }

    public void updateRoleCache(String uuid, List<String> roles) {
        redisUtils.permanentSet(getRoleKey(uuid), roles);
    }

    public void clear(String uuid) {
        redisUtils.delete(getPermKey(uuid));
        redisUtils.delete(getRoleKey(uuid));
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
