package com.yihaokezhan.hotel.common.aspect;

import com.yihaokezhan.hotel.common.config.AppConfig;
import com.yihaokezhan.hotel.common.exception.RRException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangyongfang
 * @since Wed Mar 03 2021
 */
@Aspect
@Component
public class DevAspect {

    @Autowired
    private AppConfig appConfig;

    @Pointcut("@annotation(com.yihaokezhan.hotel.common.annotation.Dev)")
    public void devPointCut() {
    }

    @Around("devPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        if (appConfig.isDev()) {
            return joinPoint.proceed();
        }
        throw new RRException("请在开发者模式下运行");
    }
}
