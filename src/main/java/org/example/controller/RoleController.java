package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Role;
import org.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/findAll")
    public Result findAll() {
        List<Role> list = roleService.findAll();
        return Result.success(list);
    }
}
