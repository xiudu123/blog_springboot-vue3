package com.xiudu.blog.config.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.config.api.ResultStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    /**
     * @param customException
     * @return 返回自定义错误
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result<?> customExceptionHandel(CustomException customException) {
        log.error("自定义错误 : ",customException);
        return Result.error(customException.getCode(), customException.getMessage());
    }

    /**
     * @param methodArgumentNotValidException
     * @return 返回参数校验错误(RequestBody校验);
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("参数校验错误(RequestBody校验) : ", methodArgumentNotValidException);
        return Result.error(1000,
                methodArgumentNotValidException.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining(", ")));
    }

    /**
     *
     * @param bindException
     * @return 返回参数校验错误(From表单校验)
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result<?> bindException(BindException bindException) {
        log.error("参数校验错误(From表单校验) : ", bindException);
        return Result.error(1000,
                bindException.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining("; ")));
    }

    /**
     *
     * @param constraintViolationException
     * @return 返回参数校验错误(单参数校验)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result<?> constraintViolationException(ConstraintViolationException constraintViolationException) {
        log.error("参数校验错误(单参数校验) : ", constraintViolationException);
        return Result.error(1000,
                constraintViolationException.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("; ")));
    }

    /**
     *
     * @param notLoginException
     * @return 返回未登录错误
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public Result<?> notLoginException(NotLoginException notLoginException) {
        log.error("未登录错误 : ", notLoginException);
        return Result.error(ResultStatus.NO_LOGIN);
    }

    /**
     *
     * @param serviceException
     * @return 接口限流
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<?> serviceException(ServiceException serviceException) {
        log.error("接口限流: ", serviceException);
        return Result.error(ResultStatus.FREQUENT_REQUEST);
    }

    /**
     *
     * @param runtimeException
     * @return 运行错误
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result<?> runtimeException(RuntimeException runtimeException) {
        log.error("运行错误 : ", runtimeException);
        return Result.error(ResultStatus.RUNTIME_ERROR);
    }

    /**
     *
     * @param exception
     * @return 系统错误
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> exception(Exception exception) {
        log.error("系统错误 : ", exception);
        return Result.error(ResultStatus.ERROR);
    }

    //定义这是一个异常处理类方法Exception异常都会经过该方法
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Result<?> exceptionHandel(Exception e) throws Exception {
//        //错误信息打印成日志
//        log.error("Exception : ",e);
//
//        if(e instanceof CustomException exception) { // 返回自定义错误;
//            return Result.error(exception.getCode(), exception.getMessage());
//        } else if(e instanceof MethodArgumentNotValidException ex) { // 返回参数校验错误(RequestBody校验);
////            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
////            ArrayList<String> errors = new ArrayList<>();
////            for(FieldError fieldError : bindingResult.getFieldErrors()) {
////                errors.add(fieldError.getDefaultMessage());
////            }
////            return Result.error(5, errors.toString());
//            return Result.error(1000,
//                    ex.getBindingResult().getAllErrors().stream()
//                            .map(ObjectError::getDefaultMessage)
//                            .collect(Collectors.joining("; "))
//            );
//        } else if (e instanceof BindException ex) { // 返回参数校验错误(From表单校验)
//            return  Result.error(1000,
//                    ex.getAllErrors().stream()
//                            .map(ObjectError::getDefaultMessage)
//                            .collect(Collectors.joining("; "))
//            );
//        } else if (e instanceof ConstraintViolationException ex) { // 返回参数校验错误(单参数校验)
//            return  Result.error(1000,
//                    ex.getConstraintViolations().stream()
//                            .map(ConstraintViolation::getMessage)
//                            .collect(Collectors.joining("; "))
//            );
//        } else if (e instanceof NotLoginException) { // 返回未登录错误
//            return Result.error(ResultStatus.NO_LOGIN);
//        } else if(e instanceof ServiceException) { // 接口限流
//            return Result.error(ResultStatus.FREQUENT_REQUEST);
//        }
//        else if(e instanceof RuntimeException){ // 返回 400;
//            return Result.error(ResultStatus.RUNTIME_ERROR);
//        } else return Result.error(ResultStatus.ERROR); // 返回其他系统错误
//    }

}