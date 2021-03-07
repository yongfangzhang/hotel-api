package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum OrderState implements BaseEnum<OrderState> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    UNPAID                      (1,         "待付款"),
    UNUSED                      (2,         "待入住"),
    UNCOMMENT                   (3,         "待评价"),
    FINISHED                    (4,         "已完成"),
    CANCELD                     (10,        "已取消"),
    ABANDON                     (11,        "已作废"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    OrderState(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public OrderState getUnknown() {
        return UNKNOWN;
    }
}
