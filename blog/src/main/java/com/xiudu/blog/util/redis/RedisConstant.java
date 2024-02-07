package com.xiudu.blog.util.redis;

import cn.hutool.core.util.RandomUtil;

/**
 * @author: 锈渎
 * @date: 2024/2/5 10:13
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于 redis 操作的常量
 */
public class RedisConstant {

    public static final String COUNTER_BLOG_VIEW_KEY = "blog:counter:view:";
    public static final String COUNTER_BLOG_FOOTER_KEY = "blog:counter:footer";
    public static final String CACHE_BLOG_TOP_KEY = "blog:footer:top";
    public static final Long CACHE_NULL_TTL = 120L;
    public static final String CACHE_USER_KEY = "blog:user:";
    public static final Long CACHE_USER_TTL = 86400L;

    public static final String CACHE_TYPE_KEY = "blog:type:";
    public static final Long CACHE_TYPE_TTL = 86400L;
    public static final String CACHE_BLOG_INFO_KEY = "blog:blog:info:";
    public static final Long CACHE_BLOG_INFO_TTL = 86400L;
    public static final String CACHE_BLOG_CONTENT_KEY = "blog:blog:content:";
    public static final Long CACHE_BLOG_CONTENT_TTL = 86400L;


}
