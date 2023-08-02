package com.atguigu.auth;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.model.system.SysRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Auther: 茶凡
 * @ClassName Test1
 * @date 2023/8/2 9:41
 * @Description 测试文件的路劲要一致 com.atguigu.auth 不然会报错
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysRoleMapperTest {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Test
    public void selectListTest(){
        List<SysRole> list = sysRoleMapper.selectList(null);
        list.forEach(System.out::println);
    }

}
