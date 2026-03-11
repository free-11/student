package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.College;

import java.util.List;

@Mapper
public interface CollegeMapper {
    List<College> findAll();
}
