package com.xiudu.blog.controller;

import com.xiudu.blog.util.limit.LimitType;
import com.xiudu.blog.util.limit.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author: 锈渎
 * @date: 2024/2/26 22:08
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@RestController
public class TestController {
    @GetMapping("/test")
    @RateLimiter(time = 1, count = 10, limitType = LimitType.IP)
    public String test() {
        return "test  " + new Date();
    }
}
