package com.yihaokezhan.hotel.common.aspect;

import javax.servlet.http.HttpServletRequest;
import com.yihaokezhan.hotel.common.annotation.NoRepeatSubmit;
import com.yihaokezhan.hotel.common.exception.RRException;
import com.yihaokezhan.hotel.common.redis.RedisService;
import com.yihaokezhan.hotel.common.shiro.ShiroUtils;
import com.yihaokezhan.hotel.model.TokenUser;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Aspect
@Component
public class NoRepeatSubmitAspect {

    @Autowired
    private RedisService redisUtils;

    @Around("execution(* com.yihaokezhan.hotel.controller.*Controller.*(..)) && @annotation(nrs)")
    public Object arround(ProceedingJoinPoint pjp, NoRepeatSubmit nrs) throws Throwable {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        TokenUser tokenUser = ShiroUtils.getTokenUser();
        if (tokenUser == null || StringUtils.isBlank(tokenUser.getUuid())) {
            return pjp.proceed();
        }
        String key = tokenUser.getUuid() + "-" + request.getServletPath();
        if (redisUtils.get(key) == null) {
            redisUtils.set(key, 0, 5); // 5s 过期
            return pjp.proceed();
        } else {
            // 如果缓存中有这个url视为重复提交
            throw new RRException("请勿重复提交");
        }
    }

}
