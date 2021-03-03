package com.yihaokezhan.hotel.common.config;

import java.io.Serializable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("yhkz.config")
public class AppConfig implements Serializable {


    /**
    *
    */
    private static final long serialVersionUID = 3575718395842760962L;

    private boolean dev;
}
