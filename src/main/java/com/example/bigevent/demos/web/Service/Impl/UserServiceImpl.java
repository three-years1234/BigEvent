package com.example.bigevent.demos.web.Service.Impl;

import com.example.bigevent.demos.web.Mapper.UserMapper;
import com.example.bigevent.demos.web.Service.UserService;
import com.example.bigevent.demos.web.entity.User;
import com.example.bigevent.demos.web.utils.MD5Util;
import com.example.bigevent.demos.web.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUsername(String username) {
        User user=userMapper.findByUsername(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        String md5Password =MD5Util.getMD5String(password);
        userMapper.add(username, md5Password);
    }

    @Override
    public void update(User user){
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl){
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id=(Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id=(Integer) map.get("id");
        userMapper.updatePwd(MD5Util.getMD5String(newPwd),id);
    }
}
