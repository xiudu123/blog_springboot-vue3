package com.xiudu.blog.config.aspect;

import cn.hutool.core.net.Ipv4Util;
import com.xiudu.blog.util.limit.LimitType;
import com.xiudu.blog.util.limit.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * @author: 锈渎
 * @date: 2024/2/26 21:30
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 接口限流切面处理
 */

@Aspect
@Component
public class RateLimiterAspect {
    private static final Logger log = LoggerFactory.getLogger(RateLimiterAspect.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisScript<Long> limitScript;

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) {
        String key = rateLimiter.key();
        int time = rateLimiter.time();
        int count = rateLimiter.count();

        String combineKey = getCombineKey(rateLimiter, point);
        try {
            Long number =
                    stringRedisTemplate.execute(limitScript,
                    Collections.singletonList(combineKey),
                    Integer.toString(count), Integer.toString(time));
            if(number == null || number.intValue() > count) {
                if(number == null) number = 0L;
                log.info("被节流: 限制请求数: {}, 当前请求数: {}, 缓存key: {}", count, number.intValue(), combineKey);
                throw new ServiceException("访问过于频繁, 请稍后再试");
            }
            else log.info("限制请求数: {}, 当前请求数: {}, 缓存key: {}", count, number.intValue(), combineKey);
        }catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("服务器限流异常，请稍后再试");
        }
    }

    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuilder stringBuilder = new StringBuilder(rateLimiter.key());
        if(rateLimiter.limitType() == LimitType.IP) {
            stringBuilder.append(((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getRemoteAddr()).append('-');
        }

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuilder.append(targetClass.getName()).append("-").append(method.getName());
        return stringBuilder.toString();
    }

}
