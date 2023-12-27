package com.xiudu.blog.controller;

import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.pojo.Blog;
import com.xiudu.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
        List<Blog> blogs = blogService.listBlogArchives();

        // 降序排序
        Map<Integer, List<Blog>> map = new TreeMap<>(Comparator.reverseOrder());
        for(Blog blog : blogs) {
            if(!map.containsKey(blog.getYear())) map.put(blog.getYear(), new ArrayList<>());
            map.get(blog.getYear()).add(blog);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("blogs", map);
        result.put("blotTotal", blogs.size());
        return Result.success(result);
    }
}
