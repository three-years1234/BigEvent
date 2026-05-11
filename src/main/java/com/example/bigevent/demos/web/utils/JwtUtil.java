package com.example.bigevent.demos.web.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static  final String KEY ="20060808";
    //得到令牌
    public static String getToken(Map<String, Object> claims){
        return JWT.create()
                .withClaim("user",claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(Algorithm.HMAC256(KEY));
    }

    //令牌解析
    public static Map<String, Object> verify(String token){
       return JWT.require(Algorithm.HMAC256(KEY))
               .build()
               .verify(token)
               .getClaim("user")
               .asMap();
    }
}
