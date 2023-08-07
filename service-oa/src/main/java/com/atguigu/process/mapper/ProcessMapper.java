package com.atguigu.process.mapper;

import com.atguigu.vo.process.ProcessQueryVo;
import com.atguigu.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @Auther: 茶凡
 * @ClassName ProcessMapper
 * @date 2023/8/7 13:22
 * @Description TODO
 */
@Repository
public interface ProcessMapper extends BaseMapper<Process> {


    IPage<ProcessVo> selectPageVo(Page<ProcessVo> page, @Param("vo") ProcessQueryVo processQueryVo);
}