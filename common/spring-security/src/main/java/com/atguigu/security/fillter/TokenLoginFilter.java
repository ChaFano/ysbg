package com.atguigu.security.fillter;

import com.alibaba.fastjson.JSON;
import com.atguigu.common.jwt.JwtHelper;
import com.atguigu.common.result.R;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.common.util.ResponseUtil;
import com.atguigu.security.custom.CustomUser;
import com.atguigu.vo.system.LoginVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 茶凡
 * @ClassName TokenLoginFilter
 * @date 2023/8/5 0:06
 * @Description 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 *
 * UsernamePasswordAuthenticationFilter 的主要作用包括：
 *
 * 提取用户名和密码：从用户提交的登录请求中提取用户名和密码，然后将其封装成认证请求。
 *
 * 构建认证请求：将提取到的用户名和密码封装成一个 UsernamePasswordAuthenticationToken 对象，作为认证请求。
 *
 * 调用 AuthenticationManager：将封装好的认证请求交给 AuthenticationManager 处理，进行实际的身份验证。
 *
 * 处理认证结果：根据认证结果，决定是否允许用户登录。如果认证成功，UsernamePasswordAuthenticationFilter
 * 会生成一个认证成功的 Authentication 对象，否则会生成一个认证失败的异常。
 *
 * 在配置 Spring Security 时，您可以自定义 UsernamePasswordAuthenticationFilter 的行为，例如更改登录路径、
 * 处理成功和失败的处理器等。通过配置，您可以适应不同的应用需求和用户界面。
 *
 * 总之，UsernamePasswordAuthenticationFilter 在 Spring Security 中负责处理基于用户名和密码的登录认证请求，它是认证流程中的重要一环。
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {


    private RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
       
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));
        this.redisTemplate = redisTemplate;
    }

    /**
     * 登录认证
     * @param req
     * @param res
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        try {
            LoginVo loginVo = new ObjectMapper().readValue(req.getInputStream(), LoginVo.class);

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());

            return this.getAuthenticationManager().authenticate(authenticationToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }

    /**
     * 登录成功
     * @param request
     * @param response
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) auth.getPrincipal();

        String token = JwtHelper.createToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());

        //保存权限数据
        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        ResponseUtil.out(response, R.ok(map));
    }

    /**
     * 登录失败
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {

        if(e.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, R.build(null, 204, e.getMessage()));
        } else {
            ResponseUtil.out(response, R.build(null, ResultCodeEnum.LOGIN_MOBLE_ERROR));
        }
    }

}
