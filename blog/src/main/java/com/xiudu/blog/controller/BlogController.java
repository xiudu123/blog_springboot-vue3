package com.xiudu.blog.controller;

import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: 锈渎
 * @date: 2023/12/14 20:40
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */

@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;


    @Operation(summary = "主页上的博客", description = "按页数获取主页上发布的所有博客")
    @Parameters({
            @Parameter(name = "pageNum", description = "页数", required = false)
    })
    @GetMapping("/index")
    public Result<?> index(@RequestParam(defaultValue = "1") Integer pageNum) {
        return Result.success(blogService.listBlogIndex(pageNum));
    }

    @Operation(summary = "搜索博客", description = "按题目名称搜索博客")
    @Parameters({
            @Parameter(name = "pageNum", description = "页数", required = false),
            @Parameter(name = "query", description = "搜索条件", required = true)
    })
    @GetMapping("/search")
    public Result<?> search(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam String query) {
        return Result.success(blogService.listBlogSearch(pageNum, query));
    }


    @Operation(summary = "根据Id获取博客信息", description = "根据Id获取博客信息")
    @Parameters({
            @Parameter(name = "blogId", description = "博客Id", required = true)
    })
    @GetMapping("/blog/id")
    public Result<?> blog(@RequestParam Long blogId) {
        return Result.success(blogService.getAndConvert(blogId));
    }

}
