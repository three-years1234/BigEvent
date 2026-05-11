package com.example.bigevent.demos.web.Mapper;

import com.example.bigevent.demos.web.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
@Insert("insert into user(username,password,create_time,update_time)"
+"values(#{username},#{password},now(),now())")
    void add(@Param("username") String username, @Param("password") String password);
@Select("select * from user where username=#{username}")
    User findByUsername(String username);
@Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);
@Update("update user set user_pic=#{avatarUrl} ,update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);
@Update("update user set password=#{newPwd} ,update_time=now() where id=#{id}")
    void updatePwd(@Param("newPwd") String newPwd, @Param("id") Integer id);
}
