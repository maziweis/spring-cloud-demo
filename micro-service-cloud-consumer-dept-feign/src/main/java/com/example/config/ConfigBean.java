package com.example.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBean {
    /**
     * OpenFeign日志增强
     * 配置OpenFeign记录哪些内容
     */
    @Bean
    Logger.Level feignLoggerLevel(){
        //Logger.Level 具体级别如下
        //NONE：不记录任何内容
        //BASIC：仅记录基本的请求信息
        //HEADERS：记录请求头信息
        //FULL：记录整个请求信息
        return Logger.Level.FULL;
    }
}
