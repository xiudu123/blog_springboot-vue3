package com.xiudu.blog.service.impl;

import com.xiudu.blog.mapper.BlogTagMapper;
import com.xiudu.blog.pojo.BlogTag;
import com.xiudu.blog.service.BlogTagService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 锈渎
 * @date: 2023/12/13 18:37
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class BlogTagServiceImpl implements BlogTagService {
    @Autowired
    private BlogTagMapper blogTagMapper;

    @Transactional
    @Override
    public void insertBlogTag(Long blogId, List<Long> ids) {
        for(Long tagId : ids) {
            blogTagMapper.insert(new BlogTag(null, blogId, tagId));
        }
    }

    @Transactional
    @Override
    public void deleteBlogTag(Long blogId) {
        blogTagMapper.deleteTagByBlogId(blogId);
    }

    @Override
    public List<Long> listTagIds(Long blogId) {
        return blogTagMapper.listTagIdsByBlogId(blogId);
    }
}
