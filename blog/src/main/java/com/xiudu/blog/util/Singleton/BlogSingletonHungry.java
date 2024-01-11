package com.xiudu.blog.util.Singleton;

import com.xiudu.blog.pojo.Blog;

import java.util.List;

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

    public List<Blog> showAllExceptContent(List<Blog> blogs) {
        for(Blog blog : blogs) {
            blog.setContentMarkdown(null);
            blog.setContentHtml(null);
        }
        return blogs;
    }

    public Blog showAllExceptContent(Blog blog) {
        blog.setContentHtml(null);
        blog.setContentMarkdown(null);
        return blog;
    }
}
