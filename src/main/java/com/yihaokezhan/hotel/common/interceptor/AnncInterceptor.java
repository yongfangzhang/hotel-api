package com.yihaokezhan.hotel.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yihaokezhan.hotel.common.annotation.Annc;
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
public class AnncInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        // not method
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Annc annc = ((HandlerMethod) handler).getMethodAnnotation(Annc.class);

        if (annc != null) {
            // 匿名
            return true;
        }
        // 需要登录

        String token = tokenUtils.getTokenFromReq(request);
        if (tokenUtils.isValidToken(token)) {
            return true;
        }
        throw new RRException(ErrorCode.ACCESS_DENIED);
    }
}
