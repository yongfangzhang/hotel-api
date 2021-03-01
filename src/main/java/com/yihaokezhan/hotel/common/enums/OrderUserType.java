package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum OrderUserType implements BaseEnum<OrderUserType> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    SOCIAL                      (1,         "散客"),
    MEMBER                      (2,         "会员"),
    COOPERATION                 (3,         "协议单位"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    OrderUserType(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public OrderUserType getUnknown() {
        return UNKNOWN;
    }
}
