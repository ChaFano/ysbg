package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.mapper.SysRoleMenuMapper;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.util.MenuHelper;
import com.atguigu.common.execption.GuiguException;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.vo.system.AssginMenuVo;
import com.atguigu.vo.system.MetaVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: 茶凡
 * @ClassName SysMenuServiceImpl
 * @date 2023/8/3 23:31
 * @Description TODO
 */

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 查询所有的 菜单路径
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {
        //全部权限列表
        List<SysMenu> sysMenuList = this.list();
        if (CollectionUtils.isEmpty(sysMenuList)) return null;
        //构建树形数据
        List<SysMenu> result = MenuHelper.buildTree(sysMenuList);
        return result;
    }

    /**
     * 根据id 删除菜单 有子菜单不能删除
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        // 当getParentId==id  且 count > 0  说明有子菜单 不允许删除
        int count = this.count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId,id));

        if(count > 0){
            throw new GuiguException(201,"菜单不能删除");
        }
        sysMenuMapper.deleteById(id);
        return false;
    }

    /**
     * 根据角色获取授权权限数据  Select 表明哪些背选中 哪些没有背选中
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //全部权限列表
        List<SysMenu> allSysMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));

        //根据角色id获取角色权限
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));

        //转换给角色id与角色权限对应Map对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(e -> e.getMenuId()).collect(Collectors.toList());

        allSysMenuList.forEach(permission -> {

            if (menuIdList.contains(permission.getId())) {
                permission.setSelect(true);
            } else {
                permission.setSelect(false);
            }
        });

        List<SysMenu> sysMenuList = MenuHelper.buildTree(allSysMenuList);

        return sysMenuList;
    }

    /**
     * 保存角色的 可以访问的菜单权限 赋予用户菜单权限 先将已有权限删除 再保存用户新权限
     * 加上事务注解 保证 原子性
     * @param  assginMenuVo
     */
    @Transactional
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {

        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, assginMenuVo.getRoleId()));

        for (Long menuId : assginMenuVo.getMenuIdList()) {

            if (StringUtils.isEmpty(menuId)) continue;

            SysRoleMenu rolePermission = new SysRoleMenu();

            rolePermission.setRoleId(assginMenuVo.getRoleId());
            rolePermission.setMenuId(menuId);
            sysRoleMenuMapper.insert(rolePermission);
        }
    }

    /**
     * 查找用户有权限访问的菜单 查找到用户访问的菜单后还需要给构建成树形结构返回给前端
     * 在获取用户基本信息的时候使用 getUserInfo 查出用户的 访问菜单和按钮权限 分开封装 再返回给前端
     *
     *
     * @param userId
     * @return
     */
    @Override
    public List<RouterVo> findUserMenuList(Long userId) {

        //超级管理员admin账号id为：1 默认可以访问所有
        List<SysMenu> sysMenuList = null;

        if (userId.longValue() == 1) {
            sysMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1).orderByAsc(SysMenu::getSortValue));
        } else {
            sysMenuList = sysMenuMapper.findListByUserId(userId);
        }
        //构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);

        // 再构建成路由数据
        List<RouterVo> routerVoList = this.buildMenus(sysMenuTreeList);
        return routerVoList;
    }

    /**
     * 获取用户按钮权限
     * @param userId
     * @return
     */
    @Override
    public List<String> findUserPermsList(Long userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;

        if (userId.longValue() == 1) {
            sysMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));
        } else {

            try {
                sysMenuList = sysMenuMapper.findListByUserId(userId);

                System.out.println(sysMenuList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // type = 2 的是按钮 查询哪些按钮用户可以访问
        List<String> permsList = sysMenuList.stream()
                .filter(item -> item.getType() == 2)
                .map(item -> item.getPerms())
                .collect(Collectors.toList());

        return permsList;
    }

    /**
     * 根据菜单构建路由
     * @param menus
     * @return
     */
    private List<RouterVo> buildMenus(List<SysMenu> menus) {

        List<RouterVo> routers = new LinkedList<RouterVo>();

        for(SysMenu menu : menus) {
            RouterVo router = new RouterVo();

            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(),menu.getIcon()));
            List<SysMenu> children = menu.getChildren();

            //如果当前是菜单，需将按钮对应的路由加载出来，如：“角色授权”按钮对应的路由在“系统管理”下面
            // type = 1 是菜单 type = 2 是按钮
            if(menu.getType().intValue() == 1){

                List<SysMenu> hiddenMenuList = children.stream()
                        .filter(item->!StringUtils.isEmpty(item.getComponent()))
                        .collect(Collectors.toList());

                for(SysMenu hiddenMenu: hiddenMenuList){

                    RouterVo hiddenRouter = new RouterVo();
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));

                    routers.add(hiddenRouter);
                }
            }else {

                if(!CollectionUtils.isEmpty(children)){
                    if(children.size()>0){
                        router.setAlwaysShow(true);
                    }
                }
                router.setChildren(buildMenus(children));
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {

        String routerPath = "/" + menu.getPath();

        if(menu.getParentId().intValue() != 0) {

            routerPath = menu.getPath();

        }
        return routerPath;
    }
}
