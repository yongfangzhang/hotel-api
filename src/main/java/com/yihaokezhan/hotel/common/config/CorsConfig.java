package com.yihaokezhan.hotel.common.config;

import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConditionalOnProperty("cors.enabled")
@ConfigurationProperties("cors")
@EnableConfigurationProperties
@Getter
@Setter
public class CorsConfig implements WebMvcConfigurer {

    private List<CorsOriginConfig> paths;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        paths.forEach(p -> {
            // @formatter:off
            registry.addMapping(p.getPattern())
                    .allowedOrigins(p.getAllowedOrigins().toArray(new String[0]))
                    .allowedMethods(p.getAllowedMethods().toArray(new String[0]))
                    .allowedHeaders(p.getAllowedHeaders().toArray(new String[0]))
                    .allowCredentials(p.isAllowCredentials())
                    .maxAge(p.getMaxAge());
            // @formatter:on
        });
    }

}
