package com.yihaokezhan.hotel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.yihaokezhan.hotel.module.mapper")
@ComponentScan("com.yihaokezhan")
public class HotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);
        System.out.println("============= started =============");
    }

}
