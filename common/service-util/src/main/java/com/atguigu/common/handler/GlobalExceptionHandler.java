package com.atguigu.common.handler;



import com.atguigu.common.execption.GuiguException;
import com.atguigu.common.result.R;
import com.atguigu.common.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;

/**
 * @Auther: 茶凡
 * @ClassName GlobalExceptionHandler
 * @date 2023/8/3 10:23
 * @Description 全局异常处理类
 *
 * 使用的是Aop 面向切面技术实现的
 * ExceptionHandler 该注解会被切入处理后 增加功能
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.fail().message("全局异常");
    }

    /**
     * 数学计算异常
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.fail().message("数学计算异常");
    }

    /**
     * 数学计算异常
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public R error(NullPointerException e){
        e.printStackTrace();
        return R.fail().message("空指针异常");
    }

    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(GuiguException.class)
    @ResponseBody
    public R error(GuiguException e){
        e.printStackTrace();
        return R.fail().message(e.getMessage()).code(e.getCode());
    }

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public R error(AccessDeniedException e) throws AccessDeniedException {
        return R.build(null, ResultCodeEnum.PERMISSION);
    }

}
