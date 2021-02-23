package com.yihaokezhan.hotel.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Getter
@Setter
public class StatelessToken implements AuthenticationToken {
    private static final long serialVersionUID = 1L;

    private String openId;

    public StatelessToken() {
    };

    public StatelessToken(String openId) {
        super();
        this.openId = openId;
    };

    @Override
    public Object getPrincipal() {
        return openId;
    }

    @Override
    public Object getCredentials() {
        return openId;
    }
}
