package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.SysUser;
import org.example.pojo.Student;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from sys_user where username=#{username} ")
    SysUser findByUserName(String username);

    @Insert("insert into sys_user(username,password) values (#{username},#{password})")
    void add(String username, String password);


}