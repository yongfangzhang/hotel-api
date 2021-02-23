package com.yihaokezhan.hotel.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class ShiroInterceptorRegister implements WebMvcConfigurer {

    @Autowired
    private ShiroInterceptor shiroInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(shiroInterceptor);
    }

}
