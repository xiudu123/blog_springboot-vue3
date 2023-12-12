package com.xiudu.blog.config.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author: 锈渎
 * @date: 2023/6/24 1:16
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 日志切面处理
 * 记录内容：请求url, 访问者ip, 调用方法classMethod, 参数args, 返回内容
 */
@Aspect
@Component
public class LogAspect {
    //获取日志类对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //定义切面方法
    @Pointcut("execution(* com.xiudu.blog.controller.*.*(..))")
    public void log(){ }

    //定义前置通知
    @Before("log()")
    public void doBefore(JoinPoint jp){
        //通过获取request上下文对象来获取request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取ip地址
        String ip = request.getRemoteAddr();
        //获取url路径
        String url = request.getRequestURL().toString();
        //通过切面对象获取类名和方法名
        String classMethod = jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName();
        //获取参数
        Object[] args = jp.getArgs();

        //封装到实体类对象中，打印成日志
        RequestLog log = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}",log);
    }
    //定义后置通知
    @After("log()")
    public void doAfter(){

    }
    //定义后置增强方法，相当于AfterReturningAdvice，方法正常退出时执行
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("Result : {}" ,result);
    }

    //定义日志实体封装类
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getClassMethod() {
            return classMethod;
        }

        public void setClassMethod(String classMethod) {
            this.classMethod = classMethod;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}