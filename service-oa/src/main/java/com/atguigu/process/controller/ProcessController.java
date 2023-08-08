package com.atguigu.process.controller;

import com.atguigu.common.result.R;
import com.atguigu.process.service.ProcessService;
import com.atguigu.vo.process.ProcessQueryVo;
import com.atguigu.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 茶凡
 * @ClassName ProcessController
 * @date 2023/8/7 13:26
 * @Description TODO
 */
@Api(tags = "审批流管理")
@RestController
@RequestMapping(value = "/admin/process")
@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProcessController {

    @Autowired
    private ProcessService processService;


    @PreAuthorize("hasAuthority('bnt.process.list')")
    @ApiOperation(value = "获取审批流分页列表")
    @GetMapping("{page}/{limit}")
    public R page(@PathVariable Long page,
                       @PathVariable Long limit,
                       ProcessQueryVo processQueryVo){
        log.info("获取审批流分页列表请求！");
        Page<ProcessVo> pageInfo = new Page<>(page, limit);
        IPage<ProcessVo> pageModel = processService.selectPage(pageInfo,processQueryVo);
        return R.ok(pageModel);
    }



}