package org.example.service.impl;

import org.example.mapper.UserMapper;
import org.example.pojo.SysUser;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUser findByUserName(String username) {
        SysUser u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        userMapper.add(username, encodedPassword);
    }

    @Override
    public void updatePassword(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        userMapper.updatePassword(username, encodedPassword);
    }
}