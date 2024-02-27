package com.xiudu.blog.util.limit;

import java.lang.annotation.*;

/**
 * Created by 锈渎 on 2024/2/26 21:15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    String key() default "limit:"; // 限流key
    int time() default 60; // 限流时间, 单位秒
    int count() default 100; // 限流次数
    LimitType limitType() default LimitType.DEFAULT; // 限流类型
}
