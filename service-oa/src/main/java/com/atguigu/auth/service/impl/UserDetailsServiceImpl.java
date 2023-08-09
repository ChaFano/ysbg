package com.atguigu.auth.service.impl;

import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.execption.GuiguException;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.model.system.SysUser;
import com.atguigu.security.custom.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: 茶凡
 * @ClassName UserDetailsServiceImpl
 * @date 2023/8/5 0:01
 * @Description  根据用户名获取用户对象（获取不到直接抛异常）
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 它是用来根据用户名加载用户信息的方法。在用户进行登录认证时，
     * Spring Security 会调用 loadUserByUsername() 方法来获取用户的详细信息，
     * 包括用户名、密码和权限等，以便进行后续的认证和授权操作。
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = sysUserService.getByUsername(username);
        if(null == sysUser) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        // 账号停用
        if(sysUser.getStatus().intValue() == 0) {

            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }

        // 获取用户按钮权限
        List<String> userPermsList = null;
        try {
            userPermsList = sysMenuService.findUserPermsList(sysUser.getId());


        } catch (Exception e) {
            e.printStackTrace();
        }

        // SimpleGrantedAuthority 是 Spring Security 中的一个类，用于表示用户的权限信息
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // 将用户权限填充到 authorities 集合中

        for (String perm : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }
        return new CustomUser(sysUser, authorities);
    }
}
