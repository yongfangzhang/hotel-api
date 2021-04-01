package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

/**
 * 字典类型
 */
@Getter
public enum DictType implements BaseEnum<DictType> {
    // @formatter:off
    UNKNOWN                           (0,         "未知"),
    ROOM_TYPE                         (1,        "房间类型"),
    SHIFT_TYPE                        (2,        "班次类型"),
    END                               (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    DictType(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public DictType getUnknown() {
        return UNKNOWN;
    }
}