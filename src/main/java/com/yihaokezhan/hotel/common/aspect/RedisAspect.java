package com.yihaokezhan.hotel.common.aspect;

import com.yihaokezhan.hotel.common.exception.RRException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Aspect
@Component
@Slf4j
public class RedisAspect {

    @Around("execution(* com.yihaokezhan.common.redis.*Utils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        try {
            result = point.proceed();
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RRException("Redis服务异常");
        }
        return result;
    }
}
