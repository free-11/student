package org.example.controller;


import org.example.pojo.SysUser;
import org.example.pojo.Result;
import org.example.service.UserService;
import org.example.utills.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;



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
        // 尝试直接比较明文密码
        if (user.getPassword().equals(u.getPassword())) {
            // 生成JWT token
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", u.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        } else {
            // 尝试使用BCrypt验证密码
            try {
                if(passwordEncoder.matches(user.getPassword(), u.getPassword())){
                    // 生成JWT token
                    Map<String, Object> claims = new HashMap<>();
                    claims.put("username", u.getUsername());
                    String token = JwtUtil.genToken(claims);
                    return Result.success(token);
                }
            } catch (Exception e) {
                // BCrypt验证失败，继续尝试其他方式
            }
        }
        return Result.error("密码错误！");
    }
    
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        
        // 验证用户是否存在
        SysUser u = userService.findByUserName(username);
        if(u == null){
            return Result.error("用户名错误！");
        }
        
        // 验证旧密码
        boolean passwordMatch = false;
        try {
            passwordMatch = passwordEncoder.matches(oldPassword, u.getPassword());
        } catch (Exception e) {
            // 尝试直接比较明文密码
            passwordMatch = oldPassword.equals(u.getPassword());
        }
        
        if(!passwordMatch){
            return Result.error("旧密码错误！");
        }
        
        // 更新密码
        userService.updatePassword(username, newPassword);
        return Result.success();
    }
    
    @GetMapping("/info")
    public Result<SysUser> getUserInfo(@RequestParam String username) {
        SysUser user = userService.findByUserName(username);
        if(user == null){
            return Result.error("用户不存在！");
        }
        // 返回实际密码
        return Result.success(user);
    }
}
