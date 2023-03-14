package com.example.service;

import com.example.entity.Dept;

import java.util.List;

public interface DeptService {
    Dept get(Integer deptNo);
    List<Dept> selectAll();
}
