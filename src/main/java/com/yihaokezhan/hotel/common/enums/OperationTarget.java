package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Getter
public enum OperationTarget implements BaseEnum<OperationTarget> {
    // @formatter:off
    UNKNOWN                             (0,         "未知"),
    SYSTEM                              (1,         "系统"),
    END            (1000,      "END");
    // @formatter:on

    private final Integer value;
    private final String name;

    OperationTarget(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    @Override
    public OperationTarget getUnknown() {
        return UNKNOWN;
    }
}
