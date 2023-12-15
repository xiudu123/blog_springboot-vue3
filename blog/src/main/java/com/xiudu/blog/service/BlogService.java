package com.xiudu.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.pojo.Blog;

import java.util.List;

/**
 * Created by 锈渎 on 2023/12/13 18:36
 */
public interface BlogService {
    int insertBlog(Blog blog);

    int deleteBlog(Long blogId);

    int updateBlog(Long blogId, Blog blog);

    Blog getBlog(Long blogId);
    Page<Blog> listBlog(Integer pageNum, Blog blogRules);
    Page<Blog> listBlogIndex(Integer pageNum);
    Page<Blog> listBlogSearch(Integer pageNum, String query);
    Page<Blog> listBlogByTypedId(Integer pageNum, Long typeId);

    List<Blog> listBlogAll();
    List<Blog> listTop(Long size);

    Blog getAndConvert(Long blogId);
    Long blogCount();
    int blogPageCount();
    int blogPageCountByTypeId(Long typeId);


}
