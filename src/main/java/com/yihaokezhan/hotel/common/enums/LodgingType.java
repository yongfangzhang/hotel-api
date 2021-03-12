package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum LodgingType implements BaseEnum<LodgingType> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    SHORT                       (1,         "短住"),
    LONG                        (2,        "长住"),
    HOUR                        (3,        "钟点"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    LodgingType(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public LodgingType getUnknown() {
        return UNKNOWN;
    }
}
