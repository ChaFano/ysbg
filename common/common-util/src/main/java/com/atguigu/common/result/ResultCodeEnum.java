package com.atguigu.common.result;

import lombok.Getter;

/**
 * @Auther: 茶凡
 * @ClassName ResultCodeEnum
 * @date 2023/8/2 10:03
 * @Description 枚举类 用于定义统一返回的状态码和信息
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限")
    ;

    private Integer code;

    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
