package com.xiudu.blog.controller;

import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.pojo.Type;
import com.xiudu.blog.service.BlogService;
import com.xiudu.blog.service.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 锈渎
 * @date: 2023/12/14 20:20
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */

@RestController
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @Operation(summary = "获取类型", description = "获取所有类型，类型当前页所有的博客以及页码")
    @Parameters({
            @Parameter(name = "pageNum", description = "获取页数, 默认第一页", required = false),
            @Parameter(name = "typeId", description = "查找的类型Id", required = true)
    })
    @GetMapping("/type")
    public Result<?> types(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam Long typeId) {
        return Result.success(blogService.listBlogByTypedId(pageNum, typeId));
    }

    @GetMapping("/types")
    public Result<?> getTypes() {

        List<Type> types = typeService.listTypeAll();

        Map<String, Long> nameToId = new HashMap<>();

        for(Type type : types) {
            nameToId.put(type.getName(), type.getId());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("records", types);
        result.put("nameToId", nameToId);
        return Result.success(result);
    }


}
