package com.example.controller;

import com.example.entity.Dept;
import com.example.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class DeptController {
    private DeptService deptService;

    public DeptController(DeptService deptService){
        this.deptService=deptService;
    }
    @Value("${server.port}")
    private String serverPort;
    @RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
    public Dept get(@PathVariable("id") int id) {
        return deptService.get(id);
    }
    @RequestMapping(value = "/dept/list", method = RequestMethod.GET)
    public List<Dept> list() {
        return deptService.selectAll();
    }
    @RequestMapping(value = "dept/feign/timeout")
    public String deptFeignTimeout(){
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return serverPort;
    }

}