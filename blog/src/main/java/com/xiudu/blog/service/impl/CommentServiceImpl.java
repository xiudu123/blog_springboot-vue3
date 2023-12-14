package com.xiudu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiudu.blog.mapper.CommentMapper;
import com.xiudu.blog.pojo.Comment;
import com.xiudu.blog.service.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 锈渎
 * @date: 2023/12/13 18:38
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Transactional
    @Override
    public int insertComment(Comment comment) {
        return commentMapper.insert(comment);
    }

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.eq("blog_id", blogId);
        return commentMapper.selectList(queryWrapper);
    }


}
