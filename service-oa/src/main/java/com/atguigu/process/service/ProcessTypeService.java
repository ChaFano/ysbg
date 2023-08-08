package com.atguigu.process.service;

import com.atguigu.model.process.ProcessType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Auther: 茶凡
 * @ClassName ProcessTypeService
 * @date 2023/8/7 10:05
 * @Description
 */
public interface ProcessTypeService extends IService<ProcessType> {
    List<ProcessType> findProcessType();
}
