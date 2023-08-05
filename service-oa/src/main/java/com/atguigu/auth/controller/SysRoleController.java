package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysRoleService;
import com.atguigu.common.result.R;
import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 茶凡
 * @ClassName SysRoleController
 * @date 2023/8/2 10:00
 * @Description 角色管理控制层
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {


    @Autowired
    private SysRoleService sysRoleService;

    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation(value = "获取全部角色列表")
    @GetMapping("findAll")
    public R<List<SysRole>> findAll() {
        List<SysRole> roleList = sysRoleService.list();

        return R.ok(roleList);
    }


    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public R pageQuery(@PathVariable Long limit , @PathVariable Long page, SysRoleQueryVo sysRoleQueryVo){

        //3 调用方法实现
        IPage<SysRole> pageModel = sysRoleService.pageQuery(page,limit,sysRoleQueryVo);
        return R.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public R get(@PathVariable Long id) {
        SysRole role = sysRoleService.getById(id);
        return R.ok(role);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public R save(@RequestBody @Validated SysRole role) {
        sysRoleService.save(role);
        return R.ok();
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public R updateById(@RequestBody SysRole role) {
        sysRoleService.updateById(role);
        return R.ok();
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable Long id) {
        sysRoleService.removeById(id);
        return R.ok();
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public R batchRemove(@RequestBody List<Long> idList) {
        sysRoleService.removeByIds(idList);
        return R.ok();
    }


    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public R toAssign(@PathVariable Long userId) {
        Map<String, Object> roleMap = sysRoleService.findRoleByAdminId(userId);
        return R.ok(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public R doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        sysRoleService.doAssign(assginRoleVo);
        return R.ok();
    }


}
