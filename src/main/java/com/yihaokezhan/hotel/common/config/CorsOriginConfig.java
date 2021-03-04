package com.yihaokezhan.hotel.common.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorsOriginConfig {
    private String pattern;
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private boolean allowCredentials = false;
    private Long maxAge = 1800L;
}
