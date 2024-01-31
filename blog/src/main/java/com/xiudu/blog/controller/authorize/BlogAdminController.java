package com.xiudu.blog.controller.authorize;

import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.pojo.Blog;
import com.xiudu.blog.pojo.BlogContent;
import com.xiudu.blog.pojo.DTO.BlogDTO;
import com.xiudu.blog.pojo.VO.blog.admin.BlogAdminUpdateVO;
import com.xiudu.blog.service.BlogContentService;
import com.xiudu.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    @Autowired
    private BlogContentService blogContentService;


    @Operation(summary = "根据条件查询博客列表", description = "根据条件查询博客列表")
    @Parameters ({
            @Parameter(name = "pageNum", description = "页码", required = false),
            @Parameter(name = "title", description = "博客标题(模糊查询)", required = false),
            @Parameter(name = "typeId", description = "标签id", required = false),
            @Parameter(name = "top", description = "是否置顶", required = false),
            @Parameter(name = "published", description = "是否发布", required = false),
            @Parameter(name = "comment", description = "是否开启评论", required = false)
    })
    @GetMapping("/get/search")
    public Result<?> getBlogSearch(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "") String title,
                                   @RequestParam(defaultValue = "-1") String typeId,
                                   @RequestParam(defaultValue = "-1") String top,
                                   @RequestParam(defaultValue = "-1") String published,
                                   @RequestParam(defaultValue = "-1") String comment) {

        Map<String, String> query= new HashMap<>();
        query.put("title", title);
        query.put("typeId", typeId);
        query.put("top", top);
        query.put("published", published);
        query.put("comment", comment);

        return Result.success(blogService.listBlogAdminQuery(pageNum, query));
    }

    @Operation(summary = "查询博客", description = "根据博客id查询博客")
    @Parameters({
            @Parameter(name = "id", description = "博客id", required = true)
    })
    @GetMapping("get/one")
    public Result<?> getBlogOne(@RequestParam Long blogId) {
        BlogAdminUpdateVO blog = blogService.getBlogUpdate(blogId);
        if(blog == null) return Result.error("该博客不存在或已被删除");
        else return Result.success(blog);
    }

    @Operation(summary = "添加博客", description = "添加博客")
    @Parameters({
            @Parameter(name = "blog", description = "博客类", required = true)
    })
    @PostMapping("/add")
    public Result<?> addBlog(@RequestBody BlogDTO blogDTO) {
        // 参数校验
        if ("".equals(blogDTO.getTitle()) || blogDTO.getTitle() == null) {
            return Result.error("请输入标题");
        }
        if(blogDTO.getTypeId() == null || blogDTO.getTypeId() < 1) {
            return Result.error("请选择分类");
        }
        if("".equals(blogDTO.getFirstPicture()) || blogDTO.getFirstPicture() == null) {
            return Result.error("请选择首图");
        }
        if("".equals(blogDTO.getContentMarkdown()) || blogDTO.getContentMarkdown() == null) {
            return Result.error("请编写博客内容");
        }
        if("".equals(blogDTO.getOverview()) || blogDTO.getOverview() == null) {
            blogDTO.setOverview("作者很懒，没有写描述");
        }
        if(blogDTO.getComment() == null) blogDTO.setComment(false);
        if(blogDTO.getTop() == null) blogDTO.setTop(false);
        // 插入
        int successInsert = blogService.insertBlog(1L, blogDTO);
        if(successInsert == 0) return Result.error("添加失败, 请稍后再试");
        else return Result.success();
    }

    @Operation(summary = "修改博客", description = "修改博客")
    @Parameters({
            @Parameter(name = "blog", description = "博客类", required = true)
    })
    @PostMapping("/update")
    public Result<?> updateBlog(@RequestBody BlogDTO newBlogDTO) {
        // 参数校验
        if("".equals(newBlogDTO.getTitle()) || newBlogDTO.getTitle() == null) {
            return Result.error("请输入标题");
        }
        if(newBlogDTO.getTypeId() == null || newBlogDTO.getTypeId() < 1) {
            return Result.error("请选择分类");
        }
        if("".equals(newBlogDTO.getFirstPicture()) || newBlogDTO.getFirstPicture() == null) {
            return Result.error("请选择首图");
        }
        if("".equals(newBlogDTO.getContentMarkdown()) || newBlogDTO.getContentMarkdown() == null) {
            return Result.error("请编写博客内容");
        }
        if("".equals(newBlogDTO.getOverview()) || newBlogDTO.getOverview() == null) {
            newBlogDTO.setOverview("作者很懒，没有写描述");
        }

        // 找到原博客
        Blog oldBlog = blogService.getBlog(newBlogDTO.getId());
        BlogContent oldBlogContent = blogContentService.getBlogContent(newBlogDTO.getId());
        if(oldBlog == null || oldBlogContent == null) {
            return Result.error("该博客不存在或已被删除");
        }

        // 假设多用户，判断是否有权限修改
        if(!check(1L, oldBlog.getUserId())) {
            return Result.error("权限不足");
        }
        if(newBlogDTO.getComment() == null) newBlogDTO.setComment(false);
        if(newBlogDTO.getTop() == null) newBlogDTO.setTop(false);

        // 修改
        int successUpdate = blogService.updateBlog(oldBlog, newBlogDTO);
        if(successUpdate == 0) return Result.error("修改失败, 请稍后再试");
        else return Result.success();
    }

    @Operation(summary = "删除博客", description = "删除博客")
    @Parameters({
            @Parameter(name = "id", description = "删除的博客Id", required = true)
    })
    @PostMapping("/delete")
    public Result<?> deleteBlog(@RequestBody Long blogId) {
        // 获取博客
        Blog blog = blogService.getBlog(blogId);

        // 博客不存在
        if(blog == null) {
            return Result.error("该博客不存在或已被删除");
        }

        // 假设是多用户，判断是否有权限删除
        if(!check(1L, blog.getUserId())) {
            return Result.error("权限不足");
        }

        // 删除
        int successDelete = blogService.deleteBlog(blogId);
        if(successDelete == 0) return Result.error("删除失败, 请稍后再试");
        else return Result.success();
    }

    boolean check(Long userId, Long blogUserId) {
        return Objects.equals(userId, blogUserId);
    }

}
