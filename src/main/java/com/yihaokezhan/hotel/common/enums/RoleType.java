package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum RoleType implements BaseEnum<RoleType> {
    // @formatter:off
    UNKNOWN              (0,     "UNKNOWN",        "未知"),
    SU                   (1,     "SU",             "超级管理员"),
    ADMIN                (2,     "ADMIN",          "管理员"),
    STAFF                (3,     "STAFF",          "员工"),
    END                  (1000,  "END",            "END");
    // @formatter:on

    private Integer value;
    private String code;
    private String name;

    RoleType(Integer v, String c, String n) {
        this.value = v;
        this.code = c;
        this.name = n;
    }

    public RoleType getUnknown() {
        return UNKNOWN;
    }
}
