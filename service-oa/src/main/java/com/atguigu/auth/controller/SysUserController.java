package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.result.R;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 茶凡
 * @ClassName SysUserController
 * @date 2023/8/3 17:39
 * @Description
 */

@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService service;

    //用户条件分页查询
    @ApiOperation("用户条件分页查询")
    @GetMapping("{page}/{limit}")
    public R index(@PathVariable Long page, @PathVariable Long limit, SysUserQueryVo sysUserQueryVo) {
        //调用mp的方法实现条件分页查询
        IPage<SysUser> pageModel = service.index(page, limit, sysUserQueryVo);
        return R.ok(pageModel);
    }


    @ApiOperation(value = "获取用户")
    @GetMapping("get/{id}")
    public R get(@PathVariable Long id) {
        SysUser user = service.getById(id);
        return R.ok(user);
    }

    @ApiOperation(value = "保存用户")
    @PostMapping("save")
    public R save(@RequestBody SysUser user) {
        service.save(user);
        return R.ok();
    }

    @ApiOperation(value = "更新用户")
    @PutMapping("update")
    public R updateById(@RequestBody SysUser user) {
        service.updateById(user);
        return R.ok();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable Long id) {
        service.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public R updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        service.updateStatus(id, status);
        return R.ok();
    }


}
