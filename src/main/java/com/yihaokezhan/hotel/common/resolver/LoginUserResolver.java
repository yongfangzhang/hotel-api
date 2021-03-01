package com.yihaokezhan.hotel.common.resolver;

import com.yihaokezhan.hotel.common.annotation.LoginUser;
import com.yihaokezhan.hotel.common.exception.ErrorCode;
import com.yihaokezhan.hotel.common.exception.RRException;
import com.yihaokezhan.hotel.common.utils.TokenUtils;
import com.yihaokezhan.hotel.model.TokenUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Component
public class LoginUserResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(TokenUser.class)
                && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
            NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        LoginUser loginUser = parameter.getParameterAnnotation(LoginUser.class);
        boolean isRequired = loginUser.required();
        // 获取用户ID
        String token = tokenUtils.getTokenFromReq(request);
        if (StringUtils.isBlank(token) && !isRequired) {
            // Token不传并且不是必须的， 允许通过
            return null;
        }
        TokenUser tokenUser = tokenUtils.getUserByToken(token);
        if (tokenUser == null || StringUtils.isBlank(tokenUser.getUuid()) || tokenUser.getAccountType() == null) {
            tokenUtils.expireToken(token);
            if (!isRequired) {
                return null;
            }
            throw new RRException(ErrorCode.ACCESS_DENIED);
        }
        long expiredAt = tokenUser.getExpiredAt();
        if (expiredAt < System.currentTimeMillis()) {
            tokenUtils.expireToken(token);
            if (!isRequired) {
                return null;
            }
            throw new RRException(ErrorCode.ACCESS_DENIED);
        }
        return tokenUser;
    }
}
