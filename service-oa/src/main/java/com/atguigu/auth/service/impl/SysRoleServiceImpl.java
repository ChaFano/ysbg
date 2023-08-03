package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Auther: 茶凡
 * @ClassName SysRoleServiceImpl
 * @date 2023/8/2 9:53
 * @Description 系统角色业务层具体代码实现
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public Page pageQuery(Long page, Long limit, SysRoleQueryVo vo) {

        Page<SysRole> pageNum = new Page<>(page,limit);

        //2 封装条件，判断条件是否为空，不为空进行封装
       QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        String roleName = vo.getRoleName();
        if(!StringUtils.isEmpty(roleName)) {
            //封装 like模糊查询
            wrapper.like("role_name",roleName);
        }
        return baseMapper.selectPage(pageNum,wrapper);
    }


}
