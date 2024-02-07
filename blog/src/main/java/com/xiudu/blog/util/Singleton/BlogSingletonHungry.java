package com.xiudu.blog.util.Singleton;

import com.xiudu.blog.config.api.ResultStatus;
import com.xiudu.blog.config.handler.CustomException;
import com.xiudu.blog.mapper.BlogContentMapper;
import com.xiudu.blog.mapper.TypeMapper;
import com.xiudu.blog.mapper.UserMapper;
import com.xiudu.blog.pojo.DO.Blog;
import com.xiudu.blog.pojo.DO.BlogContent;
import com.xiudu.blog.pojo.DO.Type;
import com.xiudu.blog.pojo.DO.User;
import com.xiudu.blog.pojo.VO.blog.*;
import com.xiudu.blog.pojo.VO.blog.admin.BlogAdminUpdateVO;
import com.xiudu.blog.pojo.VO.blog.admin.BlogAdminVO;
import com.xiudu.blog.util.redis.CacheClient;
import com.xiudu.blog.util.redis.RedisConstant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author: 锈渎
 * @date: 2024/1/10 14:07
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 单例模式： 用于 博客 类的 pojo 转换
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

    public List<BlogIndexVO> BlogToIndex(List<Blog> blogs, TypeMapper typeMapper, UserMapper userMapper, CacheClient cacheClient) {
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
                    .setTypeName(getBlogTypeName(blog.getTypeId(), typeMapper, cacheClient))
                    .setUsername(getBlogUserName(blog.getUserId(), userMapper, cacheClient)));
        }

        return blogIndexVOS;
    }

    public BlogViewVO BlogToView(Blog blog, TypeMapper typeMapper, UserMapper userMapper, BlogContentMapper blogContentMapper, CacheClient cacheClient) {

        return new BlogViewVO()
                .setId(blog.getId())
                .setTitle(blog.getTitle())
                .setFirstPicture(blog.getFirstPicture())
                .setViews(blog.getViews())
                .setComment(blog.getComment())
                .setCreateTime(blog.getCreateTime())
                .setUpdateTime(blog.getUpdateTime())
                .setUserId(blog.getUserId())
                .setTypeName(getBlogTypeName(blog.getTypeId(), typeMapper, cacheClient))
                .setUsername(getBlogUserName(blog.getUserId(), userMapper, cacheClient))
                .setContentHtml(getBlogContentHtml(blog.getId(), blogContentMapper, cacheClient));
    }

    public BlogAdminUpdateVO BlogToUpdate(Blog blog, TypeMapper typeMapper, BlogContentMapper blogContentMapper, CacheClient cacheClient) {
        return new BlogAdminUpdateVO()
                .setId(blog.getId())
                .setTitle(blog.getTitle())
                .setTypeId(blog.getTypeId())
                .setFirstPicture(blog.getFirstPicture())
                .setOverview(blog.getOverview())
                .setTop(blog.getTop())
                .setComment(blog.getComment())
                .setTypeName(getBlogTypeName(blog.getTypeId(), typeMapper, cacheClient))
                .setContentMarkdown(getBlogContentMarkdown(blog.getId(), blogContentMapper, cacheClient));
    }

    public List<BlogAdminVO> BlogToAdmin(List<Blog> blogs, TypeMapper typeMapper, CacheClient cacheClient) {
        List<BlogAdminVO> blogAdminVOS = new ArrayList<>();
        for(Blog blog : blogs) {
            blogAdminVOS.add(new BlogAdminVO()
                    .setId(blog.getId())
                    .setTitle(blog.getTitle())
                    .setTop(blog.getTop())
                    .setPublished(blog.getPublished())
                    .setComment(blog.getComment())
                    .setView(blog.getViews())
                    .setCreateTime(blog.getCreateTime())
                    .setUpdateTime(blog.getUpdateTime())
                    .setTypeName(getBlogTypeName(blog.getTypeId(), typeMapper, cacheClient)));
        }
        return blogAdminVOS;
    }

    /**
     * @param typeId 分类Id
     * @param typeMapper Mapper
     * @param cacheClient redis
     * @return 根据分类Id和redis缓存 返回分类名称
     */
    public String getBlogTypeName(Long typeId,TypeMapper typeMapper, CacheClient cacheClient) {

        Type type = cacheClient.queryWithPassThrough(RedisConstant.CACHE_TYPE_KEY, typeId, Type.class, typeMapper::selectById, RedisConstant.CACHE_TYPE_TTL, TimeUnit.SECONDS);
        if(type == null) throw new CustomException(ResultStatus.NOT_FOUND_TYPE);
        return type.getName();
    }


    /**
     *
     * @param userId 用户id
     * @param userMapper mapper
     * @param cacheClient redis
     * @return 根据用户id和redis缓存 返回用户名称
     */
    public String getBlogUserName(Long userId, UserMapper userMapper, CacheClient cacheClient) {

        User user = cacheClient.queryWithPassThrough(RedisConstant.CACHE_USER_KEY, userId, User.class, userMapper::selectById, RedisConstant.CACHE_USER_TTL, TimeUnit.SECONDS);
        if(user == null) throw new CustomException(ResultStatus.NOT_FOUNT_USER);
        return user.getUsername();
    }

    public String getBlogContentHtml(Long blogId, BlogContentMapper blogContentMapper, CacheClient cacheClient) {

        BlogContent blogContent = cacheClient.queryWithPassThrough(RedisConstant.CACHE_BLOG_CONTENT_KEY, blogId, BlogContent.class, blogContentMapper::selectById, RedisConstant.CACHE_BLOG_CONTENT_TTL, TimeUnit.SECONDS);
        if(blogContent == null) throw new CustomException(ResultStatus.NOT_FOUND_BLOG);
        return blogContent.getContentHtml();
    }

    public String getBlogContentMarkdown(Long blogId, BlogContentMapper blogContentMapper, CacheClient cacheClient) {
        BlogContent blogContent = cacheClient.queryWithPassThrough(RedisConstant.CACHE_BLOG_CONTENT_KEY, blogId, BlogContent.class, blogContentMapper::selectById, RedisConstant.CACHE_BLOG_CONTENT_TTL, TimeUnit.SECONDS);
        if(blogContent == null) throw new CustomException(ResultStatus.NOT_FOUND_BLOG);
        return blogContent.getContentMarkdown();
    }

}
