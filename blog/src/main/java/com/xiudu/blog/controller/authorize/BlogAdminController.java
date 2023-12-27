package com.xiudu.blog.controller.authorize;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.pojo.Blog;
import com.xiudu.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 锈渎
 * @date: 2023/12/15 19:13
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */

@RestController
@RequestMapping("/authorize/blog")
public class BlogAdminController {

    @Autowired
    private BlogService blogService;



    @GetMapping("/get/search")
    public Result<?> getBlogSearch(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "") String title,
                                   @RequestParam(defaultValue = "-1") String typeId,
                                   @RequestParam(defaultValue = "false") Boolean top,
                                   @RequestParam(defaultValue = "false") Boolean published) {

        Map<String, String> query= new HashMap<>();
        query.put("title", title);
        query.put("typeId", typeId);
        if(top) query.put("top", "1");
        else query.put("top", "0");
        if(published) query.put("published", "1");
        else query.put("published", "0");

        Page<Blog> blogPage = blogService.listBlogByUserIdAndQuery(pageNum, query);
        Map<String, Object> result = new HashMap<>();

        result.put("records", blogPage.getRecords());
        result.put("pageCurrent", blogPage.getCurrent());
        result.put("pageTotal", blogPage.getPages());
        result.put("pagePre", (blogPage.getCurrent() - (blogPage.hasPrevious() ? 1 : 0)));
        result.put("pageNext", (blogPage.getCurrent() + (blogPage.hasNext() ? 1 : 0)));
        return Result.success(result);
    }

    @GetMapping("get/one")
    public Result<?> getBlogOne(@RequestParam Long blogId) {
        Blog blog = blogService.getBlog(blogId);
        if(blog == null) return Result.error("该博客不存在或已被删除");
        else return Result.success(blog);
    }

    @PostMapping("/add")
    public Result<?> addBlog(@RequestBody Blog blog) {

        if ("".equals(blog.getTitle()) || blog.getTitle() == null) {
            return Result.error("请输入标题");
        }
        if(blog.getTypeId() == null || blog.getTypeId() < 1) {
            return Result.error("请选择分类");
        }
        if("".equals(blog.getFirstPicture()) || blog.getFirstPicture() == null) {
            return Result.error("请选择首图");
        }
        if("".equals(blog.getContent()) || blog.getContent() == null) {
            return Result.error("请编写博客内容");
        }
        if("".equals(blog.getOverview()) || blog.getOverview() == null) {
            blog.setOverview("作者很懒，没有写描述");
        }

        blog.setUserId(1L);
        Date date = new Date();
        blog.setCreateTime(date);
        blog.setUpdateTime(date);
        blog.setViews(0L);
        if(blog.getComment() == null) blog.setComment(false);
        if(blog.getTop() == null) blog.setTop(false);
        int successInsert = blogService.insertBlog(blog);
        if(successInsert == 0) return Result.error("添加失败, 请稍后再试");
        else return Result.success();
    }

    @PostMapping("/update")
    public Result<?> updateBlog(@RequestBody Blog newBlog) {
        if("".equals(newBlog.getTitle()) || newBlog.getTitle() == null) {
            return Result.error("请输入标题");
        }
        if(newBlog.getTypeId() == null || newBlog.getTypeId() < 1) {
            return Result.error("请选择分类");
        }
        if("".equals(newBlog.getFirstPicture()) || newBlog.getFirstPicture() == null) {
            return Result.error("请选择首图");
        }
        if("".equals(newBlog.getContent()) || newBlog.getContent() == null) {
            return Result.error("请编写博客内容");
        }
        if("".equals(newBlog.getOverview()) || newBlog.getOverview() == null) {
            newBlog.setOverview("作者很懒，没有写描述");
        }

        Blog oldBlog = blogService.getBlog(newBlog.getId());
        if(oldBlog == null) {
            return Result.error("该博客不存在或已被删除");
        }
        Date date = new Date();
        newBlog.setUserId(oldBlog.getUserId());
        newBlog.setCreateTime(oldBlog.getCreateTime());
        newBlog.setViews(oldBlog.getViews());
        newBlog.setUpdateTime(date);
        if(newBlog.getComment() == null) newBlog.setComment(false);
        if(newBlog.getTop() == null) newBlog.setTop(false);

        int successInsert = blogService.updateBlog(newBlog.getId(), newBlog);
        if(successInsert == 0) return Result.error("修改失败, 请稍后再试");
        else return Result.success();
    }

    @Operation(summary = "删除博客", description = "删除博客")
    @Parameters({
            @Parameter(name = "id", description = "删除的博客Id", required = true)
    })
    @PostMapping("/delete")
    public Result<?> deleteBlog(@RequestBody Long blogId) {
        if(blogService.getBlog(blogId) == null) {
            return Result.error("该博客不存在或已被删除");
        }

        int successDelete = blogService.deleteBlog(blogId);
        if(successDelete == 0) return Result.error("删除失败, 请稍后再试");
        else return Result.success();
    }
}
