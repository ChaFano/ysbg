package com.atguigu.common.jwt;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;


import java.util.Date;

/**
 * @Auther: 茶凡
 * @ClassName JwtHelper
 * @date 2023/8/4 15:04
 * @Description JWT 工具
 */
public class JwtHelper {

    private static long tokenExpiration = 365*24*60*60*1000;
    private static String tokenSignKey = "123456";

    /**
     * 创建token 根据账号和用户名
     * @param userId
     * @param username
     * @return
     */
    public static String createToken(Long userId, String username){

        String token = Jwts.builder()
                .setSubject("AUTH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId",userId)
                .claim("username",username)
                .signWith(SignatureAlgorithm.HS512,tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    /**
     * 根据token 获取 用户账号
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Integer userId = (Integer) claims.get("userId");
            return userId.longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据token 获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            if (StringUtils.isEmpty(token)) return "";

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("username");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//        String token = JwtHelper.createToken(1L, "admin");
//        System.out.println(token);
//        System.out.println(JwtHelper.getUserId(token));
//        System.out.println(JwtHelper.getUsername(token));
//    }



}
