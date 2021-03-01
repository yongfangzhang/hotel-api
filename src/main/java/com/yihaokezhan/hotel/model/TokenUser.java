package com.yihaokezhan.hotel.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.JSONUtils;
import com.yihaokezhan.hotel.common.utils.V;
import lombok.Getter;
import lombok.Setter;


/**
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Getter
@Setter
@JsonView(V.S.class)
public class TokenUser {

    /**
     * 账号UUID
     */
    private String uuid;

    /**
     * 租户UUID
     */
    private String tenantUuid;

    /**
     * 用户UUID
     */
    private String userUuid;

    /**
     * 账号名称
     */
    private String account;

    /**
     * 用户名称
     */
    private String name;

    /**
     * Token
     */
    private String token;

    /**
     * 账号类型
     */
    private Integer accountType;

    /**
     * 过期时间
     */
    private Long expiredAt;

    public TokenUser() {
    }

    public TokenUser(Long id, Integer type) {
        this.uuid = id.toString();
        this.accountType = type;
    }

    public TokenUser(String uuid, Integer type) {
        this.uuid = uuid;
        this.accountType = type;
    }

    public TokenUser(String uuid, Integer type, Long expiredAt) {
        this.uuid = uuid;
        this.accountType = type;
        this.expiredAt = expiredAt;
    }

    @Override
    public String toString() {
        return JSONUtils.toJSONString(this);
    }
}
