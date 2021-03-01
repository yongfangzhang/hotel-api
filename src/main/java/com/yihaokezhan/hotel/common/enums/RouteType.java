package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum RouteType implements BaseEnum<RouteType> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    MENU                        (1,         "菜单"),
    BUTTON                      (2,         "按钮"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    RouteType(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public RouteType getUnknown() {
        return UNKNOWN;
    }
}
