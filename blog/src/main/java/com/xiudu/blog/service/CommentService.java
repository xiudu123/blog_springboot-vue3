package com.xiudu.blog.service;

import com.xiudu.blog.pojo.DO.Comment;
import com.xiudu.blog.pojo.DTO.comment.CommentDTO;

import java.util.List;

/**
 * Created by 锈渎 on 2023/12/13 18:36
 */
public interface CommentService {

    int insertComment(CommentDTO comment);

    List<Comment> listCommentByBlogId(Long blogId);
}
