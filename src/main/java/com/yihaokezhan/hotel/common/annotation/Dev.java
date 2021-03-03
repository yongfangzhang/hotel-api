package com.yihaokezhan.hotel.common.annotation;

import java.lang.annotation.*;

/**
 * 开发模式接口注解
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dev {
}
