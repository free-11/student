package org.example.controller;


import org.example.pojo.SysUser;
import org.example.pojo.Result;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/register")
    public Result register(@RequestBody SysUser user) {
        SysUser u =  userService.findByUserName(user.getUsername());
        if(u !=null){
           return Result.error("用户名被占用");
        }else {
            userService.register(user.getUsername(), user.getPassword());
            return Result.success();
        }
    }
    
    @PostMapping("/login")
    public Result<String> login(@RequestBody SysUser user) {
        SysUser u =  userService.findByUserName(user.getUsername());
        if(u == null){
            return Result.error("用户名错误！");
        }
        if(passwordEncoder.matches(user.getPassword(), u.getPassword())){
            return Result.success("token");
        }
        return Result.error("密码错误！");
    }
}
