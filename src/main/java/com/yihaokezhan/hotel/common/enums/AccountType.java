package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum AccountType implements BaseEnum<AccountType> {
    // @formatter:off
    UNKNOWN              (0,     "UNKNOWN",             RoleType.UNKNOWN),
    TENANT_ADMIN         (1,     "租户管理平台",          RoleType.ADMIN),
    HOTEL_ADMIN          (2,     "酒店管理平台",          RoleType.STAFF),
    END                  (1000,  "END",                 RoleType.UNKNOWN);
    // @formatter:on

    private Integer value;
    private String name;
    private RoleType roleType;

    AccountType(Integer v, String n, RoleType r) {
        this.value = v;
        this.name = n;
        this.roleType = r;
    }

    public AccountType getUnknown() {
        return UNKNOWN;
    }
}
