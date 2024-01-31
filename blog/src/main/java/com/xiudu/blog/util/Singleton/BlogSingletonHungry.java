package com.xiudu.blog.util.Singleton;

import com.xiudu.blog.mapper.BlogContentMapper;
import com.xiudu.blog.mapper.TypeMapper;
import com.xiudu.blog.mapper.UserMapper;
import com.xiudu.blog.pojo.Blog;
import com.xiudu.blog.pojo.DTO.BlogDTO;
import com.xiudu.blog.pojo.VO.blog.*;
import com.xiudu.blog.pojo.VO.blog.admin.BlogAdminUpdateVO;
import com.xiudu.blog.pojo.VO.blog.admin.BlogAdminVO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author: 锈渎
 * @date: 2024/1/10 14:07
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
public class BlogSingletonHungry {

    private static final BlogSingletonHungry instance = new BlogSingletonHungry();

    private BlogSingletonHungry() {}

    public static BlogSingletonHungry getInstance() {
        return instance;
    }

    public List<BlogFooterVO> BlogToFooter(List<Blog> blogs) {
        List<BlogFooterVO> blogFooterDTOS = new ArrayList<>();
        for(Blog blog : blogs) {
            blogFooterDTOS.add(new BlogFooterVO()
                    .setId(blog.getId())
                    .setTitle(blog.getTitle()));
        }
        return blogFooterDTOS;
    }

    public List<BlogArchiveVO> BlogToArchive(List<Blog> blogs) {
        List<BlogArchiveVO> blogArchiveDTOS = new ArrayList<>();
        for(Blog blog : blogs) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            LocalDateTime dateTime = LocalDateTime.parse(blog.getCreateTime().toString(), formatter);

            blogArchiveDTOS.add(new BlogArchiveVO()
                    .setId(blog.getId())
                    .setTitle(blog.getTitle())
                    .setTop(blog.getTop())
                    .setYear(dateTime.getYear())
                    .setMonth(dateTime.getMonthValue())
                    .setDay(dateTime.getDayOfMonth()));
        }
        return blogArchiveDTOS;
    }

    public List<BlogIndexVO> BlogToIndex(List<Blog> blogs, TypeMapper typeMapper, UserMapper userMapper) {
        List<BlogIndexVO> blogIndexVOS = new ArrayList<>();
        for(Blog blog : blogs) {

            blogIndexVOS.add(new BlogIndexVO()
                    .setId(blog.getId())
                    .setTitle(blog.getTitle())
                    .setFirstPicture(blog.getFirstPicture())
                    .setViews(blog.getViews())
                    .setTop(blog.getTop())
                    .setOverview(blog.getOverview())
                    .setCreateTime(blog.getCreateTime())
                    .setTypeName(typeMapper.selectById(blog.getTypeId()).getName())
                    .setUsername(userMapper.selectById(blog.getUserId()).getUsername()));
        }

        return blogIndexVOS;
    }

    public BlogViewVO BlogToView(Blog blog, TypeMapper typeMapper, UserMapper userMapper, BlogContentMapper blogContentMapper) {

        return new BlogViewVO()
                .setId(blog.getId())
                .setTitle(blog.getTitle())
                .setFirstPicture(blog.getFirstPicture())
                .setViews(blog.getViews())
                .setComment(blog.getComment())
                .setCreateTime(blog.getCreateTime())
                .setUpdateTime(blog.getUpdateTime())
                .setTypeName(typeMapper.selectById(blog.getTypeId()).getName())
                .setUsername(userMapper.selectById(blog.getUserId()).getUsername())
                .setContentHtml(blogContentMapper.selectHtmlById(blog.getId()));
    }

    public BlogAdminUpdateVO BlogToUpdate(Blog blog, TypeMapper typeMapper, BlogContentMapper blogContentMapper) {
        return new BlogAdminUpdateVO()
                .setId(blog.getId())
                .setTitle(blog.getTitle())
                .setTypeId(blog.getTypeId())
                .setTypeName(typeMapper.selectById(blog.getTypeId()).getName())
                .setFirstPicture(blog.getFirstPicture())
                .setContentMarkdown(blogContentMapper.selectMarkdownById(blog.getId()))
                .setOverview(blog.getOverview())
                .setTop(blog.getTop())
                .setComment(blog.getComment());
    }

    public List<BlogAdminVO> BlogToAdmin(List<Blog> blogs, TypeMapper typeMapper) {
        List<BlogAdminVO> blogAdminVOS = new ArrayList<>();
        for(Blog blog : blogs) {
            blogAdminVOS.add(new BlogAdminVO()
                    .setId(blog.getId())
                    .setTitle(blog.getTitle())
                    .setTypeName(typeMapper.selectById(blog.getTypeId()).getName())
                    .setTop(blog.getTop())
                    .setPublished(blog.getPublished())
                    .setComment(blog.getComment())
                    .setView(blog.getViews())
                    .setCreateTime(blog.getCreateTime())
                    .setUpdateTime(blog.getUpdateTime()));
        }
        return blogAdminVOS;
    }


}
