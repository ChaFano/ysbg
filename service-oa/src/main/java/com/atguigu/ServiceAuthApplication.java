package com.atguigu;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: 茶凡
 * @ClassName ServiceAuthApplication
 * @date 2023/8/2 0:16
 * @Description
 */

@SpringBootApplication
@MapperScan(value = {"com.atguigu.auth.mapper","com.atguigu.process.mapper"})
public class ServiceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class, args);
    }

}
