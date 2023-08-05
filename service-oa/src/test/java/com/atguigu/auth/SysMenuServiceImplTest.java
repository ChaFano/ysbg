package com.atguigu.auth;

import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.service.SysMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: 茶凡
 * @ClassName SysMenuServiceImpl
 * @date 2023/8/5 17:16
 * @Description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuServiceImplTest {

    @Autowired
    SysMenuService service;

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Test
    public void test(){

        service.findUserPermsList(1l).stream().forEach(System.out::println);


    }


    @Test
    public void test2(){

        sysMenuMapper.findListByUserId(1l).stream().forEach(System.out::println);


    }









}
