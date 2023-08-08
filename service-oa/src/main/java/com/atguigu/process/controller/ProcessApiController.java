package com.atguigu.process.controller;

import com.atguigu.common.result.R;
import com.atguigu.model.process.ProcessTemplate;
import com.atguigu.process.service.ProcessService;
import com.atguigu.process.service.ProcessTemplateService;
import com.atguigu.process.service.ProcessTypeService;
import com.atguigu.vo.process.ApprovalVo;
import com.atguigu.vo.process.ProcessFormVo;
import com.atguigu.model.process.Process;

import com.atguigu.vo.process.ProcessVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 茶凡
 * @ClassName ProcessApiController
 * @date 2023/8/8 11:39
 * @Description TODO
 */
@Api(tags = "审批流管理")
@RestController
@RequestMapping(value="/admin/process")
@CrossOrigin  //跨域
public class ProcessApiController {

    @Autowired
    private ProcessTypeService processTypeService;

    @Autowired
    private ProcessTemplateService processTemplateService;

    @Autowired
    private ProcessService processService;

    @ApiOperation(value = "启动流程")
    @PostMapping("/startUp")
    public R start(@RequestBody ProcessFormVo processFormVo) {
        processService.startUp(processFormVo);
        return R.ok();
    }

    @ApiOperation(value = "待处理")
    @GetMapping("/findPending/{page}/{limit}")
    public R findPending(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<Process> pageParam = new Page<>(page, limit);

        return R.ok(processService.findPending(pageParam));
    }

    @ApiOperation(value = "获取审批模板")
    @GetMapping("getProcessTemplate/{processTemplateId}")
    public R get(@PathVariable Long processTemplateId) {
        ProcessTemplate processTemplate = processTemplateService.getById(processTemplateId);
        return R.ok(processTemplate);
    }

    @ApiOperation(value = "获取全部审批分类及模板")
    @GetMapping("findProcessType")
    public R findProcessType() {
        return R.ok(processTypeService.findProcessType());
    }

    @ApiOperation(value = "获取审批详情")
    @GetMapping("show/{id}")
    public R show(@PathVariable Long id) {
        return R.ok(processService.show(id));
    }

    @ApiOperation(value = "审批")
    @PostMapping("approve")
    public R approve(@RequestBody ApprovalVo approvalVo) {
        processService.approve(approvalVo);
        return R.ok();
    }

    @ApiOperation(value = "已处理")
    @GetMapping("/findProcessed/{page}/{limit}")
    public R findProcessed(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<Process> pageParam = new Page<>(page, limit);
        return R.ok(processService.findProcessed(pageParam));
    }

    @ApiOperation(value = "已发起")
    @GetMapping("/findStarted/{page}/{limit}")
    public R findStarted(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<ProcessVo> pageParam = new Page<>(page, limit);
        return R.ok(processService.findStarted(pageParam));
    }
}