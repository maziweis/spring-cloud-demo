package com.example.microservicecloudeureka7001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //开启Eureka server，接受其他微服务的注册
public class MicroServiceCloudEureka7001Application {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceCloudEureka7001Application.class, args);
	}

}
