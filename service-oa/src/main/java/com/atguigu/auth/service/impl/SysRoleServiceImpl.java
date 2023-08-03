package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.mapper.SysUserRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: 茶凡
 * @ClassName SysRoleServiceImpl
 * @date 2023/8/2 9:53
 * @Description 系统角色业务层具体代码实现
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

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

    @Override
    public Map<String, Object> findRoleByAdminId(Long userId) {

        // 查询所有角色
        List<SysRole> allRolesList = this.list();

        /** 根据用户id 查询其拥有的角色id
         *  select() 参数为需要的字段名称
         */
        List<SysUserRole> existUsersRoleList = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,userId)
                .select(SysUserRole::getRoleId)
        );
        // 将角色 id 收集成集合
        List<Long> existRoleIdList = existUsersRoleList.stream().map(c->c.getRoleId()).collect(Collectors.toList());

        // 对角色进行分类
        List<SysRole> assginRoleList = new ArrayList<>();
        for(SysRole role : allRolesList){
            if(existRoleIdList.contains(role.getId())){
                assginRoleList.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assginRoleList", assginRoleList);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;

    }

    @Transactional
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, assginRoleVo.getUserId()));

        for(Long roleId : assginRoleVo.getRoleIdList()) {
            if(StringUtils.isEmpty(roleId)) continue;

            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(assginRoleVo.getUserId());
            userRole.setRoleId(roleId);
            sysUserRoleMapper.insert(userRole);
        }
    }


}
