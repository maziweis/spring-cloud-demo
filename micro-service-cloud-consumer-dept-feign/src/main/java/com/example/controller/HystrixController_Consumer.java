package com.example.controller;

import com.example.service.DeptHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* Hystrix 服务熔断
熔断机制是为了应对雪崩效应而出现的一种微服务链路保护机制。
当微服务系统中的某个微服务不可用或响应时间太长时，为了保护系统的整体可用性，熔断器会暂时切断请求对该服务的调用，并快速返回一个友好的错误响应。这种熔断状态不是永久的，在经历了一定的时间后，熔断器会再次检测该微服务是否恢复正常，若服务恢复正常则恢复其调用链路。
熔断状态
在熔断机制中涉及了三种熔断状态：
熔断关闭状态（Closed）：当服务访问正常时，熔断器处于关闭状态，服务调用方可以正常地对服务进行调用。
熔断开启状态（Open）：默认情况下，在固定时间内接口调用出错比率达到一个阈值（例如 50%），熔断器会进入熔断开启状态。进入熔断状态后，后续对该服务的调用都会被切断，熔断器会执行本地的降级（FallBack）方法。
半熔断状态（Half-Open）： 在熔断开启一段时间之后，熔断器会进入半熔断状态。在半熔断状态下，熔断器会尝试恢复服务调用方对服务的调用，允许部分请求调用该服务，并监控其调用成功率。如果成功率达到预期，则说明服务已恢复正常，熔断器进入关闭状态；如果成功率仍旧很低，则重新进入熔断开启状态。
* */
@Slf4j
@RestController
@RequestMapping(value = "/consumer/dept/hystrix/")
@DefaultProperties(defaultFallback = "dept_Global_FallbackMethod") //全局的服务降级方法
public class HystrixController_Consumer {
    @Autowired
    private DeptHystrixService deptHystrixService;

    @RequestMapping(value = "ok/{id}")
    public String deptInfo_Ok(@PathVariable("id") Integer id) {
        return deptHystrixService.deptInfo_Ok(id);
    }

    //在客户端进行服务降级
    @RequestMapping(value = "timeout/{id}")
//    @HystrixCommand(fallbackMethod = "dept_TimeoutHandler") //指定降级：降级时调用的方法
    @HystrixCommand //全局降级：接口类上配置了全局的服务降级方法后，只需要添加@HystrixCommand注解即可
    //注意：全局降级方法的优先级较低，只有业务方法没有指定其降级方法时，服务降级时才会触发全局回退方法。若业务方法指定它自己的回退方法，那么在服务降级时，就只会直接触发它自己的回退方法，而非全局回退方法。
    public String deptInfo_Timeout(@PathVariable("id") Integer id) {
        String s = deptHystrixService.deptInfo_Timeout(id);
        log.info(s);
        return s;
    }

    //deptInfo_Timeout方法的 专用 fallback 方法
    public String dept_TimeoutHandler(@PathVariable("id") Integer id) {
        log.info("deptInfo_Timeout 出错，服务降级！");
        return "系统繁忙，请稍后重试！客户端降级";
    }

    /**
     * 全局的 fallback 方法，
     * 回退方法必须和 hystrix 的执行方法在相同类中
     * 注意：降级（FallBack）方法必须与其对应的业务方法在同一个类中，否则无法生效。
     *
     * @DefaultProperties(defaultFallback = "dept_Global_FallbackMethod") 类上注解，请求方法上使用 @HystrixCommand 注解
     */
    public String dept_Global_FallbackMethod() {
        return "客户端全局降级fallback办法";
    }
}
