package com.example.service;

import org.springframework.stereotype.Component;

/**
 * 不管是业务方法指定的降级方法还是全局降级方法，它们都必须和业务方法在同一个类中才能生效，业务逻辑与降级逻辑耦合度极高。
 * 下面我们对业务逻辑与降级逻辑进行解耦
 * 新建 DeptHystrixService 接口的实现类 DeptHystrixFallBackService，统一为 DeptHystrixService 中的方法提供服务降级处理
 * 注意：该类必须以组件的形式添加 Spring 容器中才能生效，最常用的方式就是在类上标注 @Component 注解。
 * 在服务绑定接口 DeptHystrixService 标注的 @FeignClient 注解中添加 fallback 属性，属性值为 DeptHystrixFallBackService.class
 */
@Component
public class DeptHystrixFallBackService implements DeptHystrixService {
    @Override
    public String deptInfo_Ok(Integer id) {
        return "DeptHystrixFallBackService实现类deptInfo_Ok方法，解耦回退";
    }

    @Override
    public String deptInfo_Timeout(Integer id) {
        return "DeptHystrixFallBackService实现类deptInfo_Timeout方法，解耦回退";
    }
}
