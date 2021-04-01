package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

@Getter
public enum DepositState implements BaseEnum<DepositState> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    NONE                        (1,         "无"),
    PAID                        (2,         "已付"),
    UNPAID                      (3,         "待付"),
    FULL_REFUNDED               (4,         "已全额退"),
    PARTIAL_REFUNDED            (5,         "已部分退"),
    END                         (1000,      "END");
    // @formatter:on

    private Integer value;
    private String name;

    DepositState(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    public DepositState getUnknown() {
        return UNKNOWN;
    }
}
