package com.yihaokezhan.hotel.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
    /**
     * required为false时允许不传Access-Token，但会检查传过来的Access-Token是否有效
     * 
     * @return boolean
     */
    boolean required() default true;
}
