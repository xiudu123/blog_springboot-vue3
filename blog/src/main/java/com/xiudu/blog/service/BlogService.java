package com.xiudu.blog.service;

import com.xiudu.blog.pojo.DO.Blog;
import com.xiudu.blog.pojo.DTO.blog.BlogDTO;
import com.xiudu.blog.pojo.VO.blog.BlogFooterVO;
import com.xiudu.blog.pojo.VO.blog.BlogIndexVO;
import com.xiudu.blog.pojo.VO.blog.BlogViewVO;
import com.xiudu.blog.pojo.VO.blog.admin.BlogAdminUpdateVO;
import com.xiudu.blog.pojo.VO.blog.admin.BlogAdminVO;
import com.xiudu.blog.util.page.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by 锈渎 on 2023/12/13 18:36
 */
public interface BlogService {
    int insertBlog(Long userId, BlogDTO blog);

    int deleteBlog(Long blogId, Long typeId, Boolean published);

    int updateBlog(Blog oldBlog, BlogDTO newBlog);

    Blog getBlog(Long blogId);
    PageInfo<BlogIndexVO> listBlogIndex(Integer pageNum);
    PageInfo<BlogIndexVO> listBlogSearch(Integer pageNum, String query);
    PageInfo<BlogIndexVO> listBlogByTypedId(Integer pageNum, Long typeId);

    Map<String, Object> listBlogArchives();
    List<BlogFooterVO> listTop(Long size);

    BlogViewVO getAndConvert(Long blogId);
    BlogAdminUpdateVO getBlogUpdate(Long blogId);
    Long blogCount();

    // 用户后台管理
    PageInfo<BlogAdminVO> listBlogAdminQuery(Integer pageNum, Map<String, String> query);
}
