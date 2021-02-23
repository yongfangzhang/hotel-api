package com.yihaokezhan.hotel.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.TimeZone;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        ObjectMapper objMapper = jackson2HttpMessageConverter.getObjectMapper();
        objMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return objMapper;
    }

}
