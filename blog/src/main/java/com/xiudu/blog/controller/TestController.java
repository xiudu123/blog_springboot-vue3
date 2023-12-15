package com.xiudu.blog.controller;

import cn.dev33.satoken.stp.StpUtil;
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
@RequestMapping("/test")

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


    @PostMapping("/login")
    @Operation(summary = "登录", description = "登录")
    public Result<?> login(@RequestParam String username, @RequestParam String password) {
        if("xiudu".equals(username) && "123456".equals(password)) {
            StpUtil.login(1);
            return Result.success("登录成功");
        }

        return Result.error( "用户名或者密码错误");
    }

    @Operation(summary = "检查是否登录", description = "检查是否登录")
    @PostMapping("/isLogin")
    public Result<?> isLogin() {
        return Result.success("当前是否登录: " + StpUtil.isLogin());
    }

    @Operation(summary = "退出登录", description = "退出登录")
    @PostMapping("/logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.success("已退出登录");
    }

    @PostMapping("/tokeInfo")
    public Result<?> tokeInfo() {
        return Result.success(StpUtil.getTokenInfo());
    }

}
