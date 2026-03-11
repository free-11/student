package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Major;

import java.util.List;

@Mapper
public interface MajorMapper {
    List<Major> findAll();
}
