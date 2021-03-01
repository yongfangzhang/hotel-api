package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum OrderType implements BaseEnum<OrderType> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    LIVE_IN                     (1,         "住店"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    OrderType(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public OrderType getUnknown() {
        return UNKNOWN;
    }
}
