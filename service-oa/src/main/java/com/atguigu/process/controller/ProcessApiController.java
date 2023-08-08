package com.atguigu.process.controller;

import com.atguigu.common.result.R;
import com.atguigu.model.process.ProcessTemplate;
import com.atguigu.process.service.ProcessTemplateService;
import com.atguigu.process.service.ProcessTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

}