package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Student;

import java.util.List;

@Mapper
public interface StudentMapper {


    int add(Student student);

    int delete(Integer studentId);

    int update(Student student);

    Student findById(Integer studentId);

    List<Student> findAll();
}
