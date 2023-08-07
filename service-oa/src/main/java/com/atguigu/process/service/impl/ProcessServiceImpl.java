package com.atguigu.process.service.impl;

import com.atguigu.process.mapper.ProcessMapper;
import com.atguigu.process.service.ProcessService;
import com.atguigu.vo.process.ProcessQueryVo;
import com.atguigu.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * @Auther: 茶凡
 * @ClassName ProcessServiceImpl
 * @date 2023/8/7 13:26
 * @Description TODO
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService {


    @Autowired
    private RepositoryService repositoryService;

    @Override
    public IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo) {

        IPage<ProcessVo> page = baseMapper.selectPageVo(pageParam, processQueryVo);
        return page;
    }

    @Override
    public void deployByZip(String deployPath) {
        // 定义zip输入流
        InputStream inputStream = this
                .getClass()
                .getClassLoader()
                .getResourceAsStream(deployPath);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        // 流程部署
        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
    }
}

