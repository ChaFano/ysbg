package com.atguigu.auth.service;

import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.Map;

/**
 * @Auther: 茶凡
 * @ClassName SysUserService
 * @date 2023/8/3 17:35
 * @Description
 */
public interface SysUserService extends IService<SysUser> {

    Page index( Long page, Long limit, SysUserQueryVo sysUserQueryVo);

    void updateStatus(Long id, Integer status);

    SysUser getByUsername(String username);

    /**
     * 根据用户名获取用户登录信息
     * @param username
     * @return
     */
    Map<String, Object> getUserInfo(String username);
}
