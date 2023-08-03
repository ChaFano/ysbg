package com.atguigu.auth.util;

import com.atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

import static org.testng.xml.dom.DomUtil.findChildren;

/**
 * @Auther: 茶凡
 * @ClassName MenuHelper
 * @date 2023/8/3 23:52
 * @Description 根据菜单数据构建菜单数据
 */
public class MenuHelper {


    /**
     * 使用递归方法建菜单
     * @param sysMenuList
     * @return
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        List<SysMenu> trees = new ArrayList<>();
        for(SysMenu sysMenu : sysMenuList){
            if(sysMenu.getParentId().longValue() == 0){
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes){

        sysMenu.setChildren(new ArrayList<SysMenu>());

        for(SysMenu it : treeNodes){
            if(sysMenu.getId().longValue() == it.getParentId().longValue()){
                if(sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return sysMenu;

    }

}
