package com.xiudu.blog.service;

import com.xiudu.blog.pojo.Comment;

import java.util.List;

/**
 * Created by 锈渎 on 2023/12/13 18:36
 */
public interface CommentService {

    int insertComment(Comment comment);

    List<Comment> listCommentByBlogId(Long blogId);
}
