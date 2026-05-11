package com.example.bigevent.demos.web.interceptors;

import com.example.bigevent.demos.web.utils.JwtUtil;
import com.example.bigevent.demos.web.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try{
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken=operations.get(token);//从Redis中获取token
            if(redisToken!=null){
                Map<String, Object> claims = JwtUtil.verify(token);
                ThreadLocalUtil.set(claims);
                return true;
            }else {
                throw new Exception();
            }
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
