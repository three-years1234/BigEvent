package com.example.bigevent.demos.web.Service;

import com.example.bigevent.demos.web.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {
    User findByUsername(String username);
    void register(String username,String  password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
