package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum RoomPriceType implements BaseEnum<RoomPriceType> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    STORE                       (1,         "门店价"),
    MEMBER                      (2,         "会员价"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    RoomPriceType(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public RoomPriceType getUnknown() {
        return UNKNOWN;
    }
}
