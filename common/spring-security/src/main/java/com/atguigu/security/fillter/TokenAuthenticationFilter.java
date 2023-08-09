package com.atguigu.security.fillter;

import com.alibaba.fastjson.JSON;
import com.atguigu.common.jwt.JwtHelper;
import com.atguigu.common.result.R;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.common.util.ResponseUtil;
import com.atguigu.security.custom.LoginUserInfoHelper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 茶凡
 * @ClassName TokenAuthenticationFilter
 * @date 2023/8/5 0:18
 * @Description 认证解析token过滤器
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("uri:"+request.getRequestURI());

        //如果是登录接口，直接放行
        if("/admin/system/index/login".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        if(null != authentication) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            ResponseUtil.out(response, R.build(null, ResultCodeEnum.PERMISSION));
        }


    }

    /**
     * HttpServletRequest 获取token 根据token 获取用户信息
     * @param request
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
//        // token置于header里
//        String token = request.getHeader("token");
//        logger.info("token:"+token);
//        if (!StringUtils.isEmpty(token)) {
//            String useruame = JwtHelper.getUsername(token);
//            logger.info("useruame:"+useruame);
//            if (!StringUtils.isEmpty(useruame)) {
//                return new UsernamePasswordAuthenticationToken(useruame, null, Collections.emptyList());
//            }
//        }
        // token置于header里
        String token = request.getHeader("token");

        logger.info("==============================================token:"+token);

        if (!StringUtils.isEmpty(token)) {

            String username = JwtHelper.getUsername(token);

            logger.info("useruame:"+username);

            if (!StringUtils.isEmpty(username)) {

                LoginUserInfoHelper.setUserId(JwtHelper.getUserId(token));
                LoginUserInfoHelper.setUsername(username);

                String authoritiesString = (String) redisTemplate.opsForValue().get(username);


                List<Map> mapList = JSON.parseArray(authoritiesString, Map.class);

                List<SimpleGrantedAuthority> authorities = new ArrayList<>();

                for (Map map : mapList) {

                    authorities.add(new SimpleGrantedAuthority((String)map.get("authority")));

                }
                authorities.stream().forEach(System.out::println);

                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            } else {

                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            }
        }
     return null;
    }
}
