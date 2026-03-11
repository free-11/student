package org.example.controller;


import org.example.pojo.Result;
import org.example.pojo.Student;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;


    @PostMapping("/add")
    public Result addStudent(@RequestBody Student student){
        studentService.add(student);
        return Result.success();
    }
    @DeleteMapping("/delete")
    public Result deleteStudent(@RequestBody Student student){
        studentService.delete(student.getStudentId());
        return Result.success();
    }

    @PostMapping("/update")
    public Result updateStudent(@RequestBody Student student){
        studentService.update(student);
        return Result.success();
    }

    @GetMapping("/findById/{studentId}")
    public Result getStudentById(@PathVariable Integer studentId){
        Student info =  studentService.findById(studentId);
        return Result.success(info);
    }
    @GetMapping("/findAll")
    public Result findAll(){
        List<Student> list = studentService.findAll();
        return Result.success(list);
    }


}