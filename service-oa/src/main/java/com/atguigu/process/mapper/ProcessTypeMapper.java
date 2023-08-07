package com.atguigu.process.mapper;

import com.atguigu.model.process.ProcessType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: 茶凡
 * @ClassName ProcessTypeMapper
 * @date 2023/8/7 10:04
 * @Description 审批类型
 */

@Mapper
public interface ProcessTypeMapper extends BaseMapper<ProcessType> {
}
