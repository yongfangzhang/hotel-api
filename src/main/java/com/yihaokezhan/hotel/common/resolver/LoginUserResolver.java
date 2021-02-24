package com.yihaokezhan.hotel.common.resolver;

import com.yihaokezhan.hotel.common.annotation.LoginUser;
import com.yihaokezhan.hotel.common.exception.ErrorCode;
import com.yihaokezhan.hotel.common.exception.RRException;
import com.yihaokezhan.hotel.common.wx.WXService;
import com.yihaokezhan.hotel.module.entity.User;
import com.yihaokezhan.hotel.module.service.IUserService;
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
    private WXService wxService;


    @Autowired
    private IUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class)
                && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
            NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        LoginUser loginUser = parameter.getParameterAnnotation(LoginUser.class);
        boolean isRequired = loginUser.required();
        // 获取用户OpenID
        String openId = wxService.getOpenIdFromRequest(request);
        if (StringUtils.isBlank(openId) && !isRequired) {
            // openId不传并且不是必须的， 允许通过
            return null;
        }

        User user = userService.mGetByOpenId(openId);

        if (user == null && isRequired) {
            throw new RRException(ErrorCode.ACCESS_DENIED);
        }
        return user;
    }
}
