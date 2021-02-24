package com.yihaokezhan.hotel.common.shiro;

import com.yihaokezhan.hotel.module.entity.User;
import com.yihaokezhan.hotel.module.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class StatelessRealm extends AuthorizingRealm {

    @Autowired
    private ShiroUtils shiroUtils;

    @Autowired
    private IUserService userService;

    public boolean supports(AuthenticationToken token) {
        // 仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        ShiroSet shiroSet = shiroUtils.getUserShiroSet(user);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(shiroSet.getPermissions());
        info.setRoles(shiroSet.getRoles());
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken)
            throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) authToken;
        String openId = statelessToken.getOpenId();
        User user = userService.mGetByOpenId(openId);

        return new SimpleAuthenticationInfo(user, statelessToken.getCredentials(), getName());
    }
}
