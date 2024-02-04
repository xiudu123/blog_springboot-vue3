package com.xiudu.blog.service;

import com.xiudu.blog.pojo.DO.BlogContent;

/**
 * Created by 锈渎 on 2024/1/30 19:26
 */
public interface BlogContentService {

    String getContentHtml(Long blogId);
    String getContentMarkdown(Long blogId);
    BlogContent getBlogContent(Long blogId);
}
