package org.example.service;

import org.example.pojo.SysUser;

public interface UserService {
    SysUser findByUserName(String username);

    void register(String username, String password);
    
    void updatePassword(String username, String password);
}
