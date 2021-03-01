package com.yihaokezhan.hotel.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yihaokezhan.hotel.common.annotation.Login;
import com.yihaokezhan.hotel.common.exception.ErrorCode;
import com.yihaokezhan.hotel.common.exception.RRException;
import com.yihaokezhan.hotel.common.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 权限(Token)验证
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        Login annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }

        String token = tokenUtils.getTokenFromReq(request);
        if (tokenUtils.isValidToken(token)) {
            return true;
        }
        throw new RRException(ErrorCode.ACCESS_DENIED);
    }
}
