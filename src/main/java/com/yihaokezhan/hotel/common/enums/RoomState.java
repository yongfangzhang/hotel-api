package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum RoomState implements BaseEnum<RoomState> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    EMPTY_CLEAN                 (1,         "空净"),
    EMPTY_DARTY                 (2,         "空脏"),
    STAY_CLEAN                  (3,         "住净"),
    STAY_DARTY                  (4,         "住脏"),
    FORBIDDEN                   (10,        "禁用"),
    APARTMENT_FORBIDDEN         (11,        "公禁"),
    APARTMENT_DELETED           (12,        "公删"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    RoomState(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public RoomState getUnknown() {
        return UNKNOWN;
    }
}
