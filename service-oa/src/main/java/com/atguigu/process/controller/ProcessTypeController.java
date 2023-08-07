package com.atguigu.process.controller;

import com.atguigu.common.result.R;
import com.atguigu.model.process.ProcessType;
import com.atguigu.process.service.ProcessTypeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 茶凡
 * @ClassName ProcessTypeController
 * @date 2023/8/7 10:07
 * @Description 
 */

@Api(value = "审批类型", tags = "审批类型")
@RestController
@RequestMapping(value = "/admin/process/processType")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProcessTypeController {

    @Autowired
    private ProcessTypeService processTypeService;

    @PreAuthorize("hasAuthority('bnt.processType.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public R index(@PathVariable Long page,
                   @PathVariable Long limit) {
        Page<ProcessType> pageParam = new Page<>(page,limit);
        IPage<ProcessType> pageModel = processTypeService.page(pageParam);
        return R.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.processType.list')")
    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public R get(@PathVariable Long id) {
        ProcessType processType = processTypeService.getById(id);
        return R.ok(processType);
    }

    @PreAuthorize("hasAuthority('bnt.processType.add')")
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public R save(@RequestBody ProcessType processType) {
        processTypeService.save(processType);
        return R.ok();
    }

    @PreAuthorize("hasAuthority('bnt.processType.update')")
    @ApiOperation(value = "修改")
    @PutMapping("update")
    public R updateById(@RequestBody ProcessType processType) {
        processTypeService.updateById(processType);
        return R.ok();
    }

    @PreAuthorize("hasAuthority('bnt.processType.remove')")
    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable Long id) {
        processTypeService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "获取全部审批分类")
    @GetMapping("findAll")
    public R findAll() {
        return R.ok(processTypeService.list());
    }
    
}
