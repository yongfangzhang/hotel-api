package com.yihaokezhan.hotel.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 防止重复提交标记注解
 */
public @interface NoRepeatSubmit {
}
