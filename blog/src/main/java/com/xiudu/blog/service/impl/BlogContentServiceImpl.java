package com.xiudu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiudu.blog.mapper.BlogContentMapper;
import com.xiudu.blog.pojo.BlogContent;
import com.xiudu.blog.service.BlogContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 锈渎
 * @date: 2024/1/30 19:26
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class BlogContentServiceImpl implements BlogContentService {
    @Autowired
    private BlogContentMapper blogContentMapper;


    @Override
    public String getContentHtml(Long blogId) {
        return blogContentMapper.selectHtmlById(blogId);
    }

    @Override
    public String getContentMarkdown(Long blogId) {
        return blogContentMapper.selectMarkdownById(blogId);
    }

    @Override
    public BlogContent getBlogContent(Long blogId) {
        return blogContentMapper.selectById(blogId);
    }
}
