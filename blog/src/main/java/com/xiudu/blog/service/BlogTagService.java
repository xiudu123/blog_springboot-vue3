package com.xiudu.blog.service;

import java.util.List;

/**
 * Created by 锈渎 on 2023/12/13 18:36
 */
public interface BlogTagService {
    void insertBlogTag(Long blogId, List<Long> ids);

    void deleteBlogTag(Long blogId);

    List<Long> listTagIds(Long blogId);
}
