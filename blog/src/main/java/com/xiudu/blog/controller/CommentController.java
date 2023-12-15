package com.xiudu.blog.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.xiudu.blog.config.api.Result;
import com.xiudu.blog.pojo.Comment;
import com.xiudu.blog.pojo.User;
import com.xiudu.blog.service.CommentService;
import com.xiudu.blog.service.UserService;
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
    @Autowired
    private UserService userService;
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
            @Parameter(name = "comment", description = "评论内容", required = true),
            @Parameter(name = "blogId", description = "博客Id", required = true),
            @Parameter(name = "topCommentId", description = "层级评论Id", required = true),
            @Parameter(name = "parentId", description = "父亲评论Id", required = true)
    })
    @PostMapping("/submit")
    public Result<?> post(@RequestBody Comment comment) {
        comment.setCreateTime(new Date());
        comment.setAvatar(avatar);
        if(StpUtil.isLogin()) {
            User user = userService.selectUserById((Long) StpUtil.getLoginId());
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
        }

        int successInsert = commentService.insertComment(comment);
        if(successInsert == 0) return Result.error("评论失败, 请稍后再试");
        else return Result.success();
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
