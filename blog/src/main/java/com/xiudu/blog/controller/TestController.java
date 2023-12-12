package com.xiudu.blog.controller;

import com.alibaba.fastjson.JSON;
import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.config.handler.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 锈渎
 * @date: 2023/12/12 15:55
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于测试接口
 */
@RestController
@RequestMapping("/tst")

public class TestController {

    @Operation(summary = "测试接口", description = "测试接口2")
    @GetMapping("/hello")
    public Result<?> returnTest() {
        return Result.success("Hello World");
    }

    @PostMapping("/tt")
    @Operation(summary = "测试接口1", description = "测试接口12")
    @Parameters({
            @Parameter(name = "name", description = "姓名", required = true)
    })
    public Result<?> return1(@RequestBody String name) {
        return Result.success(JSON.parse(name));
    }

    @PostMapping("/exception1")
    @Operation(summary = "测试报错", description = "测试运行错误")
    public Result<?> return2() {
        throw new RuntimeException();
    }
    @PostMapping("/exception2")
    @Operation(summary = "测试报错", description = "测试自定义错误")
    public Result<?> return3() {
        throw new CustomException("自定义错误");
    }

}
