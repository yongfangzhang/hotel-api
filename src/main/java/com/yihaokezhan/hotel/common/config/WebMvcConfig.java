package com.yihaokezhan.hotel.common.config;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yihaokezhan.hotel.common.interceptor.AnncInterceptor;
import com.yihaokezhan.hotel.common.interceptor.ShiroInterceptor;
import com.yihaokezhan.hotel.common.interceptor.TenantInterceptor;
import com.yihaokezhan.hotel.common.resolver.LoginUserResolver;
import com.yihaokezhan.hotel.common.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private TenantInterceptor tenantInterceptor;

    @Autowired
    private AnncInterceptor anncInterceptor;

    @Autowired
    private ShiroInterceptor shiroInterceptor;

    @Autowired
    private LoginUserResolver loginUserResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor);
        registry.addInterceptor(anncInterceptor);
        registry.addInterceptor(shiroInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserResolver);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonConverter());
        // converters.add(fastJsonConverter());
        converters.add(responseBodyConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jacksonConverter() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        // 生成json时，将所有Long转换成String
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        return jackson2HttpMessageConverter;
    }

    // 加上后 @jsonview 失效
    // @Bean
    // public FastJsonHttpMessageConverter fastJsonConverter() {
    //     FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    //     FastJsonConfig config = new FastJsonConfig();
    //     config.setSerializerFeatures( //
    //             SerializerFeature.WriteMapNullValue, // 保留map空的字段
    //             SerializerFeature.WriteNullStringAsEmpty, // 将String类型的null转成""
    //             SerializerFeature.WriteNullNumberAsZero, // 将Number类型的null转成0
    //             SerializerFeature.WriteNullListAsEmpty, // 将List类型的null转成[]
    //             SerializerFeature.WriteNullBooleanAsFalse, // 将Boolean类型的null转成false
    //             SerializerFeature.DisableCircularReferenceDetect // 避免循环引用
    //     );
    //     converter.setFastJsonConfig(config);
    //     converter.setDefaultCharset(Constant.CHARSET);
    //     List<MediaType> mediaTypeList = new ArrayList<MediaType>();
    //     // 解决中文乱码问题，相当于在Controller上的@RequestMapping中加了个属性produces = "application/json"
    //     mediaTypeList.add(MediaType.APPLICATION_JSON);
    //     converter.setSupportedMediaTypes(mediaTypeList);
    //     return converter;
    // }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Constant.CHARSET);
        return converter;
    }
}
