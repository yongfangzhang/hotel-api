package com.yihaokezhan.hotel.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.yihaokezhan.hotel.common.enums.AspectPos;
import com.yihaokezhan.hotel.common.enums.Operation;
import com.yihaokezhan.hotel.common.enums.OperationTarget;

/**
 * @author zhangyongfang
 * @since Wed Mar 03 2021
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 查看、修改......（不可带参数）
     */
    Operation operation();

    /**
     * 级别，取值范围1,2,3
     */
    int level() default 3;

    /**
     * 操作目标 注意和operation匹配
     */
    OperationTarget target() default OperationTarget.SYSTEM;

    /**
     * 详细描述(可带参数)
     */
    String description() default "";

    /**
     * 关联(可带参数)
     */
    String linked() default "";

    /**
     * 参数
     */
    String[] params() default {};

    /**
     * 执行位置
     */
    AspectPos position() default AspectPos.AFTER;
}
