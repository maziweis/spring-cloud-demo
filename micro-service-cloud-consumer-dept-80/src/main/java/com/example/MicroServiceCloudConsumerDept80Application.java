package com.example;

import com.myrule.MySelfRibbonRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient //Spring cloud Eureka 客户端，自动将本服务注册到Eureka Server 注册中心中
//自定义Ribbon 负载均衡策略，在主启动类上使用RibbonClient注解，在该微服务启动时，就能自动去加载我们自定义的Ribbon配置类，从而使配置生效
//name 为需要定制负载均衡策略的微服务名称（application name）
//configuration为定制的负载均衡策略的配置类
//且官方文档中明确提出，该配置类不能在ComponentScan注解（SpringBootApplication 注解中包含了该注解）下的包或其子包中，即不能在com.example 下
@RibbonClient(name="MICROSERVICECLOUDPROVIDERDEPT",configuration = MySelfRibbonRuleConfig.class)
public class MicroServiceCloudConsumerDept80Application {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCloudConsumerDept80Application.class, args);
    }

}
