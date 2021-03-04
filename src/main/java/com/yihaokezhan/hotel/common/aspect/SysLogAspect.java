package com.yihaokezhan.hotel.common.aspect;

import java.lang.reflect.Method;
import com.yihaokezhan.hotel.common.annotation.SysLog;
import com.yihaokezhan.hotel.common.enums.AspectPos;
import com.yihaokezhan.hotel.common.handler.DynamicTenantHandler;
import com.yihaokezhan.hotel.common.shiro.ShiroUtils;
import com.yihaokezhan.hotel.common.utils.ExpressionUtils;
import com.yihaokezhan.hotel.common.utils.IPUtils;
import com.yihaokezhan.hotel.common.utils.JSONUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.model.TokenUser;
import com.yihaokezhan.hotel.module.entity.SystemLog;
import com.yihaokezhan.hotel.module.service.ISystemLogService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private ISystemLogService systemLogService;

    @Pointcut("@annotation(com.yihaokezhan.hotel.common.annotation.SysLog)")
    public void sysLogPointCut() {
    }

    @Around("sysLogPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        SysLog sysLog = getSysLogAnnotation(joinPoint);
        Object result;
        if (sysLog.position() == AspectPos.AFTER) {
            result = joinPoint.proceed();
            sendLog(joinPoint);
        } else {
            sendLog(joinPoint);
            result = joinPoint.proceed();
        }

        return result;
    }

    private SystemLog sendLog(JoinPoint joinPoint) {
        try {
            SysLog sysLog = getSysLogAnnotation(joinPoint);
            StandardEvaluationContext context = getContext(joinPoint);
            SystemLog logModel = new SystemLog();
            TokenUser tokenUser = ShiroUtils.getTokenUser();
            if (tokenUser != null) {
                logModel.setTenantUuid(tokenUser.getTenantUuid());
                logModel.setAccountType(tokenUser.getAccountType());
                logModel.setAccountName(tokenUser.getAccount());
                logModel.setAccountUuid(tokenUser.getUuid());
            }
            logModel.setTarget(sysLog.target().getValue());
            logModel.setType(sysLog.operation().getValue());
            logModel.setLevel(sysLog.level());
            logModel.setIp(IPUtils.getIpAddr());
            logModel.setLinked(createLogLinked(sysLog, context));
            logModel.setContent(createLogContent(sysLog, context));
            if (StringUtils.isBlank(logModel.getTenantUuid())) {
                logModel.setTenantUuid(DynamicTenantHandler.getTenant());
            }
            systemLogService.mCreate(logModel);
            return logModel;
        } catch (Exception e) {
            return null;
        }
    }

    private SysLog getSysLogAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(SysLog.class);
    }

    private String createLogContent(SysLog sysLog, StandardEvaluationContext context) {
        String content = String.format("【%s】", sysLog.operation().getName());
        String description = String.format(sysLog.description(),
                JSONUtils.stringify(ExpressionUtils.eval(context, sysLog.params())));
        return content + description;
    }

    private String createLogLinked(SysLog sysLog, StandardEvaluationContext context) {
        String linked = sysLog.linked();
        if (StringUtils.isBlank(linked)) {
            return linked;
        }
        try {
            return JSONUtils.stringify(ExpressionUtils.eval1(context, linked));
        } catch (Exception e) {
            return null;
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
