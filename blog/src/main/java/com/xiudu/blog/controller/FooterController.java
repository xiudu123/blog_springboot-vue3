package com.xiudu.blog.controller;

import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 锈渎
 * @date: 2023/12/15 20:11
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */

@RestController
@RequestMapping("/footer")
public class FooterController {

    @Autowired
    private BlogService blogService;

    @Operation(summary = "获取最新发布的3条博客", description = "获取最新发布的3条博客")
    @GetMapping("/newBlog")
    public Result<?> footerNewBlogs() {
        return Result.success(blogService.listTop(3L));
    }

    @Operation(summary = "博客总数", description = "博客总数")
    @GetMapping("/message")
    public Result<?> footerBlogCount() {
        return Result.success(blogService.blogCount());
    }
}
