package org.example.service.impl;

import org.example.mapper.StudentMapper;

import org.example.pojo.Student;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Override
    public void add(Student student) {
        studentMapper.add(student);
    }

    @Override
    public void delete(Integer studentId) {
        studentMapper.delete(studentId);
    }

    @Override
    public void update(Student student) {
        studentMapper.update(student);
    }

    @Override
    public Student findById(Integer studentId) {
        return studentMapper.findById(studentId);
    }

    @Override
    public List<Student> findAll() {
        return studentMapper.findAll();
    }


}
