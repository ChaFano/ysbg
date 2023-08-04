package com.atguigu.auth.mapper;

import com.atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 茶凡
 * @ClassName SysMenuMapper
 * @date 2023/8/3 23:29
 * @Description TODO
 */

@Repository
@Mapper
public interface SysMenuMapper  extends BaseMapper<SysMenu> {
    List<SysMenu> findListByUserId(@Param("userId") Long userId);

}
