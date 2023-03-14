package com.example.service;

import com.example.entity.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@FeignClient(value = "MICROSERVICECLOUDPROVIDERDEPT")//服务提供者在注册中心的名字，即服务的配置文件application.yml下spring.application.name
public interface DeptFeignService {

    @RequestMapping(value = "/dept/get/{id}",method = RequestMethod.GET)
    Dept get(@PathVariable("id") int id);

    @RequestMapping(value = "/dept/list",method = RequestMethod.GET)
    List<Dept> list();
}
