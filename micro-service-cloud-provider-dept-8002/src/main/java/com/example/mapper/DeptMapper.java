package com.example.mapper;

import com.example.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptMapper {
    Dept selectByPrimaryKey(Integer deptNo);
    List<Dept> GetAll();
}
