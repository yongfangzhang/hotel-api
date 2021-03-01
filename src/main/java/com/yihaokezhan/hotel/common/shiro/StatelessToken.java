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

    private String uuid;
    private String token;

    public StatelessToken() {
    };

    public StatelessToken(String uuid, String token) {
        super();
        this.uuid = uuid;
        this.token = token;
    };

    @Override
    public Object getPrincipal() {
        return uuid;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
