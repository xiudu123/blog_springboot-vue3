package com.xiudu.blog.config.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.xiudu.blog.config.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

/**
 * @author: 锈渎
 * @date: 2023/6/24 0:46
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 自定义错误拦截器, 同一处理页面错误判断
 */

@RestControllerAdvice // 拦截所有标注有RestController注解的错误
@Slf4j
public class ControllerExceptionHandler {

    //定义这是一个异常处理类方法Exception异常都会经过该方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> exceptionHandel(Exception e) throws Exception {
        //错误信息打印成日志
        log.error("Exception : ",e);

        if(e instanceof CustomException exception) { // 返回自定义错误;
            return Result.error(exception.getCode(), exception.getMessage());
        } else if(e instanceof MethodArgumentNotValidException) { // 返回参数校验错误;
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            ArrayList<String> errors = new ArrayList<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }
            return Result.error(5, errors.toString());
        } else if (e instanceof NotLoginException) {
            return Result.error(6, "请登录");
        } else if(e instanceof RuntimeException){ // 返回 400;
            return Result.error("Runtime Error");
        } else return Result.error("系统错误"); // 返回其他系统错误
    }

}