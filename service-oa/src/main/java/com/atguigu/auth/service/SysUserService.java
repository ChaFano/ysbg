package com.atguigu.auth.service;

import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther: 茶凡
 * @ClassName SysUserService
 * @date 2023/8/3 17:35
 * @Description
 */
public interface SysUserService extends IService<SysUser> {

    Page index( Long page, Long limit, SysUserQueryVo sysUserQueryVo);

}
