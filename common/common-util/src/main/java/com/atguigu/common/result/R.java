package com.atguigu.common.result;

import lombok.Data;

/**
 * @Auther: 茶凡
 * @ClassName Result
 * @date 2023/8/2 10:02
 * @Description 控制层返回结果类定义 用于统一返回 状态码码 、消息、数据
 */
@Data
public class R<T> {


    //返回码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private T data;

    public R(){}

    /**
     * 返回数据 如果 data 不为空就将数据封装到 返回类的 data里面
     * protected 同一个类、同一个包、不同包的子类中 可以使用
     * @param data
     * @param <T>
     * @return
     */
    protected static <T> R<T> build(T data){
        R<T> result = new R<T>();
        if(data != null){
            result.setData(data);
        }
        return result;
    }


    /**
     * 带自定义状态码和消息的
     * @param body
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> R<T> build(T body, Integer code, String message) {
        R<T> result = build(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 带枚举的消息
     * @param body
     * @param resultCodeEnum
     * @param <T>
     * @return
     */
    public static <T> R<T> build(T body, ResultCodeEnum resultCodeEnum) {
        R<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }


    /**
     * 操作成功
     * @param <T>
     * @return
     */
    public static<T> R<T> ok(){
        return R.ok(null);
    }

    /**
     * 操作成功
     * @param data  baseCategory1List
     * @param <T>
     * @return
     */
    public static<T> R<T> ok(T data){
        R<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    /**
     * 操作失败
     * @param <T>
     * @return
     */
    public static<T> R<T> fail(){
        return R.fail(null);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> R<T> fail(T data){
        R<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    public R<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public R<T> code(Integer code){
        this.setCode(code);
        return this;
    }

}
