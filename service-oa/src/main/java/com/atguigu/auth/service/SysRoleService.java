package com.atguigu.auth.service;

import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Auther: 茶凡
 * @ClassName SysRoleService
 * @date 2023/8/2 9:51
 * @Description SysRoleService
 */
public interface SysRoleService extends IService<SysRole> {

    Page pageQuery(Long page, Long limit, SysRoleQueryVo vo);


}
