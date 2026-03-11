package org.example.controller;

import org.example.pojo.College;
import org.example.pojo.Result;
import org.example.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/college")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping("/findAll")
    public Result findAll() {
        List<College> list = collegeService.findAll();
        return Result.success(list);
    }
}
