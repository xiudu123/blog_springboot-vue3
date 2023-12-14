package com.xiudu.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.pojo.Blog;
import com.xiudu.blog.pojo.Type;
import com.xiudu.blog.service.BlogService;
import com.xiudu.blog.service.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        List<Type> types = typeService.listTypeAll();
        int pageCount = blogService.blogPageCountByTypeId(typeId);

        if((pageCount < pageNum) || (pageNum < 1)) {
            if(pageNum < 1) pageNum = 1;
            if(pageNum > pageCount) pageNum = pageCount;
        }

        Page<Blog> page = blogService.listBlogByTypedId(pageNum, typeId);

        int buttonCount = 5; // 分页数量按钮
        int startPage = Math.max(1, (int)page.getCurrent() - buttonCount / 2); // 计算起始页码
        int endPage = Math.min(startPage + buttonCount - 1, (int)page.getPages()); // 计算结束页码

        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .toList(); // 生成页码页表
        Map<String, Object> result = new HashMap<>();
        result.put("types", types);
        result.put("pageNumbers", pageNumbers);
        result.put("pageInfo", page);
        result.put("prePage", (page.getCurrent() - (page.hasPrevious() ? 1 : 0)));
        result.put("nextPage", (page.getCurrent() + (page.hasNext() ? 1 : 0)));
        result.put("typeId", typeId);
        return Result.success(result);
    }

}
