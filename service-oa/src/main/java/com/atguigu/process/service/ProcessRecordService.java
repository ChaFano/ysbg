package com.atguigu.process.service;

import com.atguigu.model.process.ProcessRecord;
import com.atguigu.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Auther: 茶凡
 * @ClassName ProcessRecordService
 * @date 2023/8/8 14:25
 * @Description TODO
 */
public interface ProcessRecordService extends IService<ProcessRecord> {

    void record(Long processId, Integer status, String description);


}
