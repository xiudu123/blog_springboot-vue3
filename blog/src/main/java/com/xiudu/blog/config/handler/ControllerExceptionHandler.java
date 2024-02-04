package com.xiudu.blog.config.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.config.api.ResultStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        } else if(e instanceof MethodArgumentNotValidException ex) { // 返回参数校验错误(RequestBody校验);
//            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
//            ArrayList<String> errors = new ArrayList<>();
//            for(FieldError fieldError : bindingResult.getFieldErrors()) {
//                errors.add(fieldError.getDefaultMessage());
//            }
//            return Result.error(5, errors.toString());
            return Result.error(1000,
                    ex.getBindingResult().getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof BindException ex) { // 返回参数校验错误(From表单校验)
            return  Result.error(1000,
                    ex.getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof ConstraintViolationException ex) { // 返回参数校验错误(单参数校验)
            return  Result.error(1000,
                    ex.getConstraintViolations().stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof NotLoginException) { // 返回未登录错误
            return Result.error(ResultStatus.NO_LOGIN);
        } else if(e instanceof RuntimeException){ // 返回 400;
            return Result.error(ResultStatus.RUNTIME_ERROR);
        } else return Result.error(ResultStatus.ERROR); // 返回其他系统错误
    }

}