package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum RouteType implements BaseEnum<RouteType> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    ALL                         (1,         "所有"),
    GET                         (2,         "查看"),
    CREATE                      (3,         "创建"),
    UPDATE                      (4,         "更新"),
    DELETE                      (5,         "删除"),
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
