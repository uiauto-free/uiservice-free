package com.uiautofree.common.config.jwtoken;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class JwtokenHelper {
    private static final long TOKEN_TIMEOUT = 1000 * 60 * 24 * 30;
    public static String createToken(JSONObject jsonObject){
        try {
            Long id = jsonObject.getLong("id");
            String userCode = jsonObject.getString("userCode");
            String userName = jsonObject.getString("userName");
            String userNickName = jsonObject.getString("userNickName");
            String userId = jsonObject.getString("userId");

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", id);
            claims.put("userName", userName);
            claims.put("userNickName", userNickName);
            claims.put("userCode", userCode);
            claims.put("userId", userId);
            long currentTime = System.currentTimeMillis();
            String tokenString = Jwts.builder().setIssuer(null)
                    .setSubject("userName")
                    .setAudience(null)
                    .setExpiration(new Date(currentTime + TOKEN_TIMEOUT))
                    .setId(UUID.randomUUID().toString())
                    .addClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, userId)
                    .compact();
            return tokenString;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成 jwt 错误");
        }
    }

    public static Map<String, Object> verifyToken(String token) {
        try {
            log.info("current token is " + token);
            String userId = JWT.decode(token).getClaim("userId").asString();
            Claims claims = Jwts.parser().setSigningKey(userId).parseClaimsJws(token).getBody();
            Map<String, Object> result = new HashMap<>();
            claims.forEach((key, value)-> {
                result.put(key, value);
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
