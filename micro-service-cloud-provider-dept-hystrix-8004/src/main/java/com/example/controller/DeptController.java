package com.example.controller;

import com.example.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dept/hystrix/")
@Slf4j
public class DeptController {
    @Autowired
    private DeptService deptService;

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "ok/{id}")
    public String deptInfo_Ok(@PathVariable("id") Integer id) {
        String result = deptService.deptInfo_Ok(id);
        log.info("端口号：" + serverPort + "  result:" + result);
        return result + "s,  端口号：" + serverPort;
    }

    //Hystrix服务超时降级
    @RequestMapping(value = "timeout/{id}")
    public String deptInfo_Timeout(@PathVariable("id") Integer id) {
        String result = deptService.deptInfo_Timeout(id);
        log.info("端口号：" + serverPort + "  result:" + result);
        return result + "s，  端口号：" + serverPort;
    }

    // Hystrix 服务熔断
    @RequestMapping(value = "circuit/{id}")
    public String deptCircuitBreaker(@PathVariable("id") Integer id) {
        String result = deptService.deptCircuitBreaker(id);
        log.info("result:" + result);
        return result;
    }
}
