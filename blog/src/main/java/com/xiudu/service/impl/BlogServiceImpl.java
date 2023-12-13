package com.xiudu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.config.handler.CustomException;
import com.xiudu.blog.mapper.*;
import com.xiudu.blog.pojo.Blog;
import com.xiudu.blog.pojo.Tag;
import com.xiudu.service.BlogService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
    @Autowired
    private BlogTagMapper blogTagMapper;

    @Autowired
    private TagMapper tagMapper;

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
        return blogMapper.deleteById(blogId);
    }

    @Override
    public Blog getBlog(Long blogId) {
        Blog blog = blogMapper.selectById(blogId);
        if(blog == null) {
            throw new CustomException(404, "该博客不存在");
        }
        blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
        blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
        blog.setTagList(blogTags(blogId));
        return blog;
    }

    @Override
    public Page<Blog> listBlog(Integer pageNum, Blog blogRules) {
        Page<Blog> page = new Page<>(pageNum, 10);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();

        // 查询条件
        if(!isEmpty(blogRules.getTitle())) {
            queryWrapper.like("title", blogRules.getTitle());
        }
        if(!isEmpty(blogRules.getTypeId()) && blogRules.getTypeId() > 0) {
            queryWrapper.eq("type_id", blogRules.getTypeId());
        }
        if(!isEmpty(blogRules.getTop()) && blogRules.getTop()) {
            queryWrapper.eq("top", blogRules.getTop());
        }
        if(!isEmpty(blogRules.getPublished()) && !blogRules.getPublished()) {
            queryWrapper.eq("published", blogRules.getPublished());
        }
        queryWrapper.orderByDesc("update_time");

        Page<Blog> blogPage = blogMapper.selectPage(page, queryWrapper);
        for(Blog blog : blogPage.getRecords()) {
            blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
            blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
        }

        return blogPage;
    }

    @Override
    public Page<Blog> listBlogIndex(Integer pageNum) {
        Page<Blog> page = new Page<>(pageNum, 10);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "create_time");
        Page<Blog> blogPage = blogMapper.selectPage(page, queryWrapper);
        for(Blog blog : blogPage.getRecords()) {
            blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
            blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
        }
        return blogPage;
    }

    @Override
    public Page<Blog> listBlogSearch(Integer pageNum, String query) {
        Page<Blog> page = new Page<>(pageNum, 10);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "create_time");
        queryWrapper.like("title", query);
        Page<Blog> blogPage = blogMapper.selectPage(page, queryWrapper);
        for(Blog blog : blogPage.getRecords()) {
            blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
            blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
        }
        return blogPage;
    }

    @Override
    public Page<Blog> listBlogByTypedId(Integer pageNum, Long typeId) {
        Page<Blog> page = new Page<>(pageNum, 10);

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top");
        queryWrapper.orderByDesc("update_time");
        if(typeMapper.selectCountByTypeId(typeId) > 0) queryWrapper.eq("type_id", typeId);

        Page<Blog> blogPage = blogMapper.selectPage(page, queryWrapper);
        for(Blog blog : blogPage.getRecords()) {
            blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
            blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
        }
        return blogPage;
    }

    @Override
    public List<Blog> listBlogAll() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "update_time");
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        for(Blog blog : blogs) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            LocalDateTime dateTime = LocalDateTime.parse(blog.getUpdateTime().toString(), formatter);
            blog.setYear(dateTime.getYear());
            blog.setMonth(dateTime.getMonthValue());
            blog.setDay(dateTime.getDayOfMonth());
        }
        return blogs;
    }

    @Override
    public List<Blog> listTop(Long size) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "update_time").last("LIMIT " + size.toString());
        return blogMapper.selectList(queryWrapper);
    }


    @Override
    public int blogCount() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1);
        return Math.toIntExact(blogMapper.selectCount(queryWrapper));
    }


    @Override
    public Blog getAndConvert(Long blogId) { // 渲染博客;
        Blog blog = getBlog(blogId);
        String content = blog.getContent();
        blog.setContent(content);
        blog.setViews(blog.getViews() + 1);
        blogMapper.updateViewById(blogId);
        return null;
    }




    List<Tag> blogTags(Long blogId) {
        List<Long> tagIds = blogTagMapper.listTagIdsByBlogId(blogId);
        if(tagIds == null || tagIds.size() == 0) return null;
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.or().in("id", tagIds);
        return tagMapper.selectList(queryWrapper);
    }

    Boolean isEmpty(String object) {
        return "".equals(object) || object == null;
    }
    Boolean isEmpty(Boolean object) {
        return object == null;
    }
    Boolean isEmpty(Long object) {
        return object == null;
    }

}
