package com.example.service.impl;

import com.example.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
    @Override
    public String deptInfo_Ok(Integer id) {
        return "线程池" + Thread.currentThread().getName() + "  deptInfo_Ok,id" + id;
    }

    //一旦该方法失败并抛出了异常信息后，会自动调用 @HystrixCommand注解标注的fallbackMethod指定的方法
    @HystrixCommand(fallbackMethod = "dept_TimeoutHandler",
            //规定5秒钟以内就不报错，超过5秒就报错，调用指定的方法
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")})
    @Override
    public String deptInfo_Timeout(Integer id) {
        int outTime = 4;
        try {
            Thread.sleep(outTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "  deftInfo_Timeout,id" + id + "  耗时：" + outTime;
    }

    @Override
    @HystrixCommand(fallbackMethod = "deptCircuitBreaker_fallback", commandProperties = {
            //以下参数在HystrixCommandProperties类中有默认配置
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启熔断器
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1000"), //统计时间窗
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //统计时间窗内请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //休眠时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //在统计时间窗口期以内，请求失败率达到 60% 时进入熔断状态
    })
    public String deptCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("id不能是负数");
        }
        String serialNum = UUID.randomUUID().toString();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号为：" + serialNum;
    }

    public String dept_TimeoutHandler(Integer id) {
        return "系统繁忙，请稍后重试！" + "线程池：" + Thread.currentThread().getName() + "  deptInfo_Timeout,id:  " + id;
    }

    public String deptCircuitBreaker_fallback(Integer id) {
        return "id 不能是负数,请稍后重试!\t id:" + id;
    }
}
