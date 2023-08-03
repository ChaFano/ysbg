package com.atguigu.auth.config.mp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: 茶凡
 * @ClassName MybatisPlusConfig
 * @date 2023/8/3 9:20
 * @Description MybatisPlus 分页插件
 */

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        //1.先new一个拦截器的类
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //2.添加一个或多个拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //3.把拦截器返回
        return interceptor;
    }


}
