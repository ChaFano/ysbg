package com.atguigu.common.config.mp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Auther: 茶凡
 * @ClassName MybatisPlusConfig
 * @date 2023/8/2 23:17
 * @Description MybatisPlus 分页插件
 */
@MapperScan("com.guigu.mapper")
@Configuration
public class MybatisPlusConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */

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
