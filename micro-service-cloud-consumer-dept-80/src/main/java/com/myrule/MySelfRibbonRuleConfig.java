package com.myrule;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRibbonRuleConfig {
    @Bean
    public IRule myRule(){
        return new MyRandomRule(); //使用自定义配置类，3次一轮换
    }
}
