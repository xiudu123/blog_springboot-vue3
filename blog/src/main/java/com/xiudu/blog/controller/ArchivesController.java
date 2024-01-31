package com.xiudu.blog.controller;

import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: 锈渎
 * @date: 2023/12/14 19:28
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@RestController
public class ArchivesController {
    @Autowired
    private BlogService blogService;

    @Operation(summary = "时间轴", description = "按照时间返回所有博客信息")
    @GetMapping("/archives")
    public Result<?> archives() {
        return Result.success(blogService.listBlogArchives());
    }
}
