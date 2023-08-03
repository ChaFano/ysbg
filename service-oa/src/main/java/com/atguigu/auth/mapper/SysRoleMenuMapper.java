package com.atguigu.auth.mapper;

import com.atguigu.model.system.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 茶凡
 * @ClassName SysRoleMenuMapper
 * @date 2023/8/4 0:26
 * @Description
 */
@Repository
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

}
