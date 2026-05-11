package com.example.bigevent.demos.web.Controller;

import com.example.bigevent.demos.web.Service.UserService;
import com.example.bigevent.demos.web.entity.Result;
import com.example.bigevent.demos.web.entity.User;
import com.example.bigevent.demos.web.utils.JwtUtil;
import com.example.bigevent.demos.web.utils.MD5Util;
import com.example.bigevent.demos.web.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
     private UserService userService;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password){
        User user=userService.findByUsername(username);
        if(user==null){
            userService.register(username, password);
            return Result.success();
        }else{
            return Result.error("用户已存在");
        }
    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username,
                        @Pattern(regexp = "^\\S{5,16}$") String password){
        User user=userService.findByUsername(username);
        if(user==null){
            return Result.error("用户不存在");
        }else{
            String md5Password = MD5Util.getMD5String(password);
            if(user.getPassword().equals(md5Password)){
                Map<String,Object> claims=new HashMap<>();
                claims.put("id",user.getId());
                claims.put("username",user.getUsername());
                String token= JwtUtil.getToken(claims);
                ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
                operations.set(token,token,1000 * 60 * 60 * 24, TimeUnit.SECONDS);
                return Result.success(token);
            }else{
                return Result.error("密码错误");
            }
        }
    }

    @GetMapping("/userinfo")
    public Result<User> userinfo(){
        Map<String,Object> map=ThreadLocalUtil.get();
        String username=(String) map.get("username");
        User user=userService.findByUsername(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
       userService.updateAvatar(avatarUrl);
       return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,
                            @RequestHeader("Authorization") String token){
        //参数校验
        String oldPwd=params.get("oldPwd");
        String newPwd=params.get("newPwd");
        String rePwd=params.get("rePwd");
        if(oldPwd==null||newPwd==null||rePwd==null){
            return Result.error("参数错误");
        }
        //1.校验原密码
        Map<String,Object> map=ThreadLocalUtil.get();
        String username=(String) map.get("username");
        User user=userService.findByUsername(username);
        if(!user.getPassword().equals(MD5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }
        //2.校验newPwd和rePwd
        if(!newPwd.equals(rePwd)){
            return Result.error("两次密码不一致");
        }
        //3.原密码与新密码校验
        if(oldPwd.equals(newPwd)){
            return Result.error("新密码不能与原密码一致");
        }
        userService.updatePwd(newPwd);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
