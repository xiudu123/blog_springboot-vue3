package com.xiudu.blog.controller;

import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.pojo.Comment;
import com.xiudu.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author: 锈渎
 * @date: 2023/12/14 19:41
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@RequestMapping("/comments")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Value("${comment.avatar}")
    private String avatar;

    @Operation(summary = "获取评论", description = "根据博客的Id或许所属的所有评论的树形结构")
    @Parameters({
            @Parameter(name = "blogId", description = "博客Id", required = true)
    })
    @GetMapping("/obtain")
    public Result<?> comments(@RequestParam Long blogId) {
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        Map<Long, List<Comment>> childrenComment = eachChildrenComment(comments);
        Map<Long, String> idToName = new HashMap<>();
        for(Comment comment : comments) idToName.put(comment.getId(), comment.getNickname());
        if(childrenComment.containsKey(-1L)) Collections.reverse(childrenComment.get(-1L));

        Map<String, Object> result = new HashMap<>();
        result.put("comments", childrenComment.get(-1L));
        result.put("replyComments", childrenComment);
        result.put("idToName", idToName);
        return Result.success(result);
    }

    @Operation(summary = "提交评论（待完成）", description = "提交评论")
    @Parameters({
            @Parameter(name = "comment", description = "评论内容", required = true)
    })
    @PostMapping("/submit")
    public Result<?> post(@RequestBody Comment comment) {

        return Result.success("提交成功");
    }

    // map<parentId, comments>
    private Map<Long, List<Comment>> eachChildrenComment(List<Comment> comments){
        Map<Long, List<Comment> > childrenComment = new HashMap<>();
        for(Comment comment : comments){
            if(!childrenComment.containsKey(comment.getTopCommentId()))  childrenComment.put(comment.getTopCommentId(), new ArrayList<>());
            childrenComment.get(comment.getTopCommentId()).add(comment);
        }
        return childrenComment;
    }

}
