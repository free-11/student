package org.example.service;

import org.example.pojo.Result;
import org.example.pojo.Student;

import java.util.List;

public interface StudentService {
    //新增学生信息
    void add(Student student);

    void delete(Integer studentId);

    void update(Student student);

    Student findById(Integer studentId);


    List<Student> findAll();
}
