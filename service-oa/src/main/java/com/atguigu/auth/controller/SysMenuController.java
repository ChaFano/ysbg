package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysMenuService;
import com.atguigu.common.result.R;
import com.atguigu.model.system.SysMenu;
import com.atguigu.vo.system.AssginMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 茶凡
 * @ClassName SysMenuController
 * @date 2023/8/3 23:38
 * @Description TODO
 */

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "获取菜单")
    @GetMapping("findNodes")
    public R findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return R.ok(list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public R save(@RequestBody SysMenu permission) {
        sysMenuService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public R updateById(@RequestBody SysMenu permission) {
        sysMenuService.updateById(permission);
        return R.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable Long id) {
        sysMenuService.removeById(id);
        return R.ok();
    }



    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable Long roleId) {
        List<SysMenu> list = sysMenuService.findSysMenuByRoleId(roleId);
        return R.ok(list);
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public R doAssign(@RequestBody AssginMenuVo assignMenuVo) {
        sysMenuService.doAssign(assignMenuVo);
        return R.ok();
    }



}
