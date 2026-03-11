package org.example.controller;

import org.example.pojo.Major;
import org.example.pojo.Result;
import org.example.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/major")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping("/findAll")
    public Result findAll() {
        List<Major> list = majorService.findAll();
        return Result.success(list);
    }
}
