package com.yihaokezhan.hotel.common.shiro;

import com.yihaokezhan.hotel.common.utils.TokenUtils;
import com.yihaokezhan.hotel.model.TokenUser;
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
    private TokenUtils tokenUtils;

    public boolean supports(AuthenticationToken token) {
        // 仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        TokenUser tokenUser = (TokenUser) principals.getPrimaryPrincipal();
        ShiroSet shiroSet = shiroUtils.getAccountShiroSet(tokenUser);
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
        String token = statelessToken.getToken();
        TokenUser tokenUser = tokenUtils.getUserByToken(token);

        return new SimpleAuthenticationInfo(tokenUser, token, getName());
    }
}
