package com.yihaokezhan.hotel.common.aspect;

import java.lang.reflect.Method;
import com.yihaokezhan.hotel.common.annotation.DataSource;
import com.yihaokezhan.hotel.common.exception.RRException;
import com.yihaokezhan.hotel.common.handler.DynamicTenantHandler;
import com.yihaokezhan.hotel.common.utils.ExpressionUtils;
import com.yihaokezhan.hotel.common.utils.M;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class DataSourceAspect implements Ordered {

    @Pointcut("@annotation(com.yihaokezhan.hotel.common.annotation.DataSource)")
    public void dataSourcePointCut() {

    }

    @Before("dataSourcePointCut()")
    public void before(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        DataSource ds = method.getAnnotation(DataSource.class);

        DynamicTenantHandler.setTenant(getTenant(point, ds.tenant()));
    }

    @After("dataSourcePointCut()")
    public void after(JoinPoint point) {
        DynamicTenantHandler.clearTenant();
    }

    @Override
    public int getOrder() {
        return 1;
    }


    private String getTenant(JoinPoint point, String tenantExpression) {
        try {
            Object v = ExpressionUtils.eval1(getContext(point), tenantExpression);
            if (v == null || StringUtils.isBlank(v.toString())) {
                throw new RRException("租户表达式获取失败");
            }
            log.info("tenant from aop {}", v.toString());
            return v.toString();
        } catch (Exception e) {
            throw new RRException("租户表达式获取失败:" + e.getMessage());
        }
    }

    private StandardEvaluationContext getContext(JoinPoint joinPoint) {
        M vars = M.m();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] params = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        try {
            for (int i = 0; i < params.length; i++) {
                vars.put(params[i], args[i]);
            }
        } catch (Exception ignored) {
        }
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariables(vars);
        return context;
    }
}
