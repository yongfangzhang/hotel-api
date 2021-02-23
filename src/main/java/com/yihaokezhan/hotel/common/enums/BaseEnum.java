package com.yihaokezhan.hotel.common.enums;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public interface BaseEnum<E> {
    Object getValue();

    String getName();

    default E getUnknown() {
        return null;
    };
}
