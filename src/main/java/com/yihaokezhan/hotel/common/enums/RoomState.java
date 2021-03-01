package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum RoomState implements BaseEnum<RoomState> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    NORMAL                      (1,         "正常"),
    UNCLEAN                     (2,         "待打扫"),
    FORBIDDEN                   (10,        "已禁用"),
    DELETED                     (11,        "已删除"),
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