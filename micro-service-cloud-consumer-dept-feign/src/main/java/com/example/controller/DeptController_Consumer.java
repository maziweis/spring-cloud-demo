package com.example.controller;

import com.example.entity.Dept;
import com.example.service.DeptFeignService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "consumer/dept/")
public class DeptController_Consumer {

    @Resource
    private DeptFeignService deptFeignService;

    @RequestMapping(value = "get/{id}")
    public Dept get(@PathVariable("id")Integer id){
        return deptFeignService.get(id);
    }

    @RequestMapping(value = "list")
    public List<Dept> list(){
        return deptFeignService.list();
    }
    @RequestMapping(value = "feign/timeout")
    public String DeptFeignTimeout(){
        //openFeign-ribbon客户端一般默认等待一秒钟，超过该时间就会报错
        return deptFeignService.DeptTimeout();
    }
}
