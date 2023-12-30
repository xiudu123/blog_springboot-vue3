package com.xiudu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.config.handler.CustomException;
import com.xiudu.blog.mapper.*;
import com.xiudu.blog.pojo.Blog;
import com.xiudu.blog.service.BlogService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author: 锈渎
 * @date: 2023/12/13 18:37
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public int insertBlog(Blog blog) {
        return blogMapper.insert(blog);
    }

    @Transactional
    @Override
    public int deleteBlog(Long blogId) {
        return blogMapper.deleteById(blogId);
    }

    @Transactional
    @Override
    public int updateBlog(Long blogId, Blog blog) {
        return blogMapper.updateById(blog);
    }

    @Override
    public Blog getBlog(Long blogId) {
        Blog blog = blogMapper.selectById(blogId);
        if(blog == null) {
            throw new CustomException(404, "该博客不存在");
        }
        blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
        blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
        return blog;
    }


    @Override
    public Page<Blog> listBlogIndex(Integer pageNum) {
        Page<Blog> page = new Page<>(pageNum, 10);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "create_time");
        return getBlogPage(page, queryWrapper);
    }

    @Override
    public Page<Blog> listBlogSearch(Integer pageNum, String query) {
        Page<Blog> page = new Page<>(pageNum, 10);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "create_time");
        queryWrapper.like("title", query);
        return getBlogPage(page, queryWrapper);
    }

    @Override
    public Page<Blog> listBlogByTypedId(Integer pageNum, Long typeId) {
        Page<Blog> page = new Page<>(pageNum, 10);

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top");
        queryWrapper.orderByDesc("create_time");
        if(typeMapper.selectCountByTypeId(typeId) > 0) queryWrapper.eq("type_id", typeId);

        return getBlogPage(page, queryWrapper);
    }



    @Override
    public List<Blog> listBlogArchives() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc( "create_time");
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        for(Blog blog : blogs) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            LocalDateTime dateTime = LocalDateTime.parse(blog.getUpdateTime().toString(), formatter);
            blog.setContentHtml(null);
            blog.setContentMarkdown(null);
            blog.setYear(dateTime.getYear());
            blog.setMonth(dateTime.getMonthValue());
            blog.setDay(dateTime.getDayOfMonth());
        }
        return blogs;
    }

    @Override
    public List<Blog> listTop(Long size) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "create_time").last("LIMIT " + size.toString());
        List<Blog> blogs= blogMapper.selectList(queryWrapper);
        for(Blog blog : blogs) {
            blog.setContentHtml(null);
            blog.setContentMarkdown(null);
        }
        return blogs;
    }

    @Override
    public Long blogCount() {
        return blogMapper.selectBlogCount();
    }


    @Override
    public Blog getAndConvert(Long blogId) { // 渲染博客;
        Blog blog = getBlog(blogId);
        String content = blog.getContentHtml();
        blog.setContentHtml(content);
        blog.setViews(blog.getViews() + 1);
        blogMapper.updateViewById(blogId);
        return blog;
    }


    @Override
    public Page<Blog> listBlogAdminQuery(Integer pageNum, Map<String, String> query) {
        Page<Blog> page = new Page<>(pageNum, 10);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc( "create_time");

        if(!("".equals(query.get("title"))))       queryWrapper.like("title", query.get("title"));
        if(!("-1".equals(query.get("typeId"))))    queryWrapper.eq("type_id", query.get("typeId"));
        if(!("-1".equals(query.get("top"))))       queryWrapper.eq("top", query.get("top"));
        if(!("-1".equals(query.get("published")))) queryWrapper.eq("published", query.get("published"));
        if(!("-1".equals(query.get("comment"))))   queryWrapper.eq("comment", query.get("comment"));
        return getBlogPage(page, queryWrapper);
    }

    private Page<Blog> getBlogPage(Page<Blog> page, QueryWrapper<Blog> queryWrapper) {
        Page<Blog> blogPage = blogMapper.selectPage(page, queryWrapper);
        for(Blog blog : blogPage.getRecords()) {
            blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
            blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
        }
        return blogPage;
    }

}
