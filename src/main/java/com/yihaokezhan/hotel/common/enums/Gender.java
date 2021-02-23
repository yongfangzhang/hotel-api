package com.yihaokezhan.hotel.common.enums;

import lombok.Getter;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Getter
public enum Gender implements BaseEnum<Gender> {
    // @formatter:off
    UNKNOWN                     (0,         "未知"),
    MALE                        (1,         "男"),
    FEMALE                      (2,         "女"),
    END                         (1000,      "END");
    // @formatter:on

    private final Integer value;
    private final String name;

    Gender(Integer v, String n) {
        this.value = v;
        this.name = n;
    }

    @Override
    public Gender getUnknown() {
        return UNKNOWN;
    }
}
