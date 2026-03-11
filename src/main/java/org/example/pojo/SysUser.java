package org.example.pojo;

import lombok.Data;

// 类名必须是大驼峰：SysUser（对应数据库表sys_user）
@Data
public class SysUser {
    private Integer userId;   // 对应表中user_id（主键）
    private String username;  // 对应表中username
    private String password;  // 对应表中password
}