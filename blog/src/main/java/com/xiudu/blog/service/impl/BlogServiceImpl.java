package com.xiudu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.config.api.ResultStatus;
import com.xiudu.blog.config.handler.CustomException;
import com.xiudu.blog.mapper.*;
import com.xiudu.blog.pojo.DO.Blog;
import com.xiudu.blog.pojo.DO.BlogContent;
import com.xiudu.blog.pojo.DO.Type;
import com.xiudu.blog.pojo.DTO.blog.BlogDTO;
import com.xiudu.blog.pojo.VO.blog.BlogArchiveVO;
import com.xiudu.blog.pojo.VO.blog.BlogFooterVO;
import com.xiudu.blog.pojo.VO.blog.BlogViewVO;
import com.xiudu.blog.pojo.VO.blog.admin.BlogAdminUpdateVO;
import com.xiudu.blog.service.BlogService;
import com.xiudu.blog.util.Singleton.BlogSingletonHungry;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

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
    private BlogContentMapper blogContentMapper;

    /**
     *
     * @param userId 用户id
     * @param blogDTO 博客
     * @return 0 - 插入失败; 1 - 插入成功
     */
    @Transactional
    @Override
    public int insertBlog(Long userId, BlogDTO blogDTO) {
        Date date = new Date();
        if(blogDTO.getPublished()) {
            Long typeId = blogDTO.getTypeId();
            typeMapper.addCountById(typeId);
        }

        Blog blog = new Blog()
                .setTitle(blogDTO.getTitle())
                .setFirstPicture(blogDTO.getFirstPicture())
                .setViews(0L)
                .setTop(blogDTO.getTop())
                .setPublished(blogDTO.getPublished())
                .setComment(blogDTO.getComment())
                .setCreateTime(date)
                .setUpdateTime(date)
                .setOverview(blogDTO.getOverview())
                .setUserId(userId)
                .setTypeId(blogDTO.getTypeId());
        int insertSuccess1 = blogMapper.insert(blog);

        BlogContent blogContent = new BlogContent()
                .setId(blog.getId())
                .setContentHtml(blogDTO.getContentHtml())
                .setContentMarkdown(blogDTO.getContentMarkdown());


        int insertSuccess2 = blogContentMapper.insert(blogContent);
        if((insertSuccess1 & insertSuccess2) == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
            return 0;
        }
        return 1;
    }

    /**
     * @param blogId 博客Id
     * @return 0 - 删除成功, 1 - 删除失败
     */
    @Transactional
    @Override
    public int deleteBlog(Long blogId, Long typeId) {
        typeMapper.deleteById(typeId);
        int deleteSuccess1 = blogMapper.deleteById(blogId);
        int deleteSuccess2 = blogContentMapper.deleteById(blogId);
        if((deleteSuccess1 & deleteSuccess2) == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
            return 0;
        }
        return 1;
    }

    /**
     *
     * @param oldBlog 修改之前的博客
     * @param newBlogDTO 修改之后的博客
     * @return 0 - 修改成功, 1 - 修改失败
     */
    @Transactional
    @Override
    public int updateBlog(Blog oldBlog, BlogDTO newBlogDTO) {
        Date date = new Date();

        if(oldBlog.getPublished()) {
            Long oldTypeId = oldBlog.getTypeId();
            typeMapper.deleteCountById(oldTypeId);
        }
        if(newBlogDTO.getPublished()) {
            Long newTypeId = newBlogDTO.getTypeId();
            typeMapper.addCountById(newTypeId);
        }

        Blog blog = new Blog()
                .setId(newBlogDTO.getId())
                .setTitle(newBlogDTO.getTitle())
                .setFirstPicture(newBlogDTO.getFirstPicture())
                .setTop(newBlogDTO.getTop())
                .setPublished(newBlogDTO.getPublished())
                .setComment(newBlogDTO.getComment())
                .setOverview(newBlogDTO.getOverview())
                .setTypeId(newBlogDTO.getTypeId())
                .setUpdateTime(date)
                .setCreateTime(oldBlog.getCreateTime())
                .setUserId(oldBlog.getUserId())
                .setViews(oldBlog.getViews());
        int updateSuccess1 = blogMapper.updateById(blog);
        BlogContent blogContent = new BlogContent()
                .setId(newBlogDTO.getId())
                .setContentHtml(newBlogDTO.getContentHtml())
                .setContentMarkdown(newBlogDTO.getContentMarkdown());

        int updateSuccess2 = blogContentMapper.updateById(blogContent);
        if((updateSuccess1 & updateSuccess2) == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
            return 0;
        }

        return 1;
    }

    /**
     *
     * @param blogId 博客Id
     * @return 根据博客Id返回的博客
     */
    @Override
    public Blog getBlog(Long blogId) {
        Blog blog = blogMapper.selectById(blogId);
        if(blog == null) {
            throw new CustomException(ResultStatus.NOT_FOUND_BLOG);
        }
        return blog;
    }

    /**
     *
     * @param pageNum 分页当前页
     * @return 博客列表
     * @description: 首页的博客分页列表
     */
    @Override
    public Map<String, Object> listBlogIndex(Integer pageNum) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "create_time");
        return getBlogPage(pageNum, queryWrapper, 1);
    }

    // TODO 首页搜索
    @Override
    public Map<String, Object> listBlogSearch(Integer pageNum, String query) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "create_time");
        queryWrapper.like("title", query);
        return getBlogPage(pageNum, queryWrapper, 1);
    }

    /**
     * @param pageNum 分页当前页
     * @param typeId 标签Id
     * @return 根据标签Id返回的博客列表
     */
    @Override
    public Map<String, Object> listBlogByTypedId(Integer pageNum, Long typeId) {

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top");
        queryWrapper.orderByDesc("create_time");
        if(typeMapper.selectCountByTypeId(typeId) > 0) queryWrapper.eq("type_id", typeId);

        return getBlogPage(pageNum, queryWrapper, 2);
    }


    /**
     *
     * @return 按时间降序排列的博客列表, 降序排的年，博客总数
     * @description: 时光轴上的博客列表
     * @sql
     * SELECT id, title, top, create_time FROM blog <br>
     * WHERE published = 1 <br>
     * ORDER BY create_time DESC <br>
     */
    @Override
    public Map<String, Object> listBlogArchives() {
        // sql查询
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "title", "top", "create_time")
                .eq("published", 1)
                .orderByDesc( "create_time");

        BlogSingletonHungry blogSingletonHungry = BlogSingletonHungry.getInstance();
        List<BlogArchiveVO> blogArchiveDTOS = blogSingletonHungry.BlogToArchive(blogMapper.selectList(queryWrapper));

        // 将博客按时间降序排序
        Map<Integer, List<BlogArchiveVO>> map = new TreeMap<>(Comparator.reverseOrder());
        ArrayList<Integer> year = new ArrayList<>();
        for(BlogArchiveVO blog : blogArchiveDTOS) {
            if(!map.containsKey(blog.getYear())) {
                map.put(blog.getYear(), new ArrayList<>());
                year.add(blog.getYear());
            }
            map.get(blog.getYear()).add(blog);
        }

        // 逆序排序;
        year.sort(Comparator.reverseOrder());

        Map<String, Object> result = new HashMap<>();
        result.put("blogs", map);
        result.put("years", year);
        result.put("blotTotal", blogArchiveDTOS.size());

        return result;
    }

    /**
     * @param size Long 页脚博客数量
     * @return 最新发布前 size 的博客列表
     * @sql
     * SELECT id, title FROM blog <br>
     * WHERE published = 1 <br>
     * ORDER BY top DESC, create_time DESC <br>
     * LIMIT {size}
     */
    @Override
    public List<BlogFooterVO> listTop(Long size) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "title")
                .eq("published", 1)
                .orderByDesc( "create_time")
                .last("LIMIT " + size.toString());
        List<Blog> blogs = blogMapper.selectList(queryWrapper);

        BlogSingletonHungry blogSingletonHungry = BlogSingletonHungry.getInstance();
        return blogSingletonHungry.BlogToFooter(blogs);
    }

    /**
     *
     * @return 博客总数
     * @sql SELECT COUNT(*) FROM blog  WHERE published = 1
     */
    @Override
    public Long blogCount() {
        return blogMapper.selectBlogCount();
    }


    /**
     *
     * @param blogId 博客id
     * @return 博客
     * @description: 根据id获取博客所有信息，并使浏览量 +1
     */
    @Override
    public BlogViewVO getAndConvert(Long blogId) { // 渲染博客;
        Blog blog = getBlog(blogId);
        if(!blog.getPublished()) {
            throw new CustomException(ResultStatus.NO_PERMISSION);
        }
        blog.setViews(blog.getViews() + 1);
        blogMapper.updateViewById(blogId);
        return BlogSingletonHungry.getInstance().BlogToView(blog, typeMapper, userMapper, blogContentMapper);
    }

    @Override
    public BlogAdminUpdateVO getBlogUpdate(Long blogId) {
        Blog blog = getBlog(blogId);
        return BlogSingletonHungry.getInstance().BlogToUpdate(blog, typeMapper, blogContentMapper);
    }


    /**
     * @param pageNum 分页当前页
     * @param query 查询条件
     * @return 根据查询条件返回的博客列表
     */
    @Override
    public Map<String, Object> listBlogAdminQuery(Integer pageNum, Map<String, String> query) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc( "create_time");

        if(!("-1".equals(query.get("typeId"))))    queryWrapper.eq("type_id", query.get("typeId"));
        if(!("-1".equals(query.get("top"))))       queryWrapper.eq("top", query.get("top"));
        if(!("-1".equals(query.get("published")))) queryWrapper.eq("published", query.get("published"));
        if(!("-1".equals(query.get("comment"))))   queryWrapper.eq("comment", query.get("comment"));
        if(!("".equals(query.get("title"))))       queryWrapper.like("title", query.get("title"));

        return getBlogPage(pageNum, queryWrapper, 3);
    }

    // TODO limit深度查询优化
    /**
     * @param pageNum 分页的当前页
     * @param queryWrapper 查询条件
     * @return 博客列表
     * @description: 博客分页查询 <br>
     * 1 - 首页 博客列表 <br>
     * 2 - 根据分类 博客列表 <br>
     * 3 - 管理员搜索 博客列表 <br>
     */
    private Map<String, Object> getBlogPage(Integer pageNum, QueryWrapper<Blog> queryWrapper, int op) {
        if(pageNum <= 0) pageNum = 1;
        Page<Blog> page = new Page<>(pageNum, 10);
        Page<Blog> blogPage = blogMapper.selectPage(page, queryWrapper);
        // 如果 pageNum 超过了总页数, 则重新查询最后一页
        if(blogPage.getCurrent() > blogPage.getPages()) {
            blogPage = blogMapper.selectPage(new Page<>(blogPage.getPages(), 10), queryWrapper);
        }


        Map<String, Object> result = new HashMap<>();
        BlogSingletonHungry blogSingletonHungry = BlogSingletonHungry.getInstance();
//        result.put("pageInfo", blogPage);
        if(op == 1 || op == 2) {
            result.put("records", blogSingletonHungry.BlogToIndex(blogPage.getRecords(), typeMapper, userMapper));
        }else if(op == 3) {
            result.put("records", blogSingletonHungry.BlogToAdmin(blogPage.getRecords(), typeMapper));
        }
//        result.put("records", blogPage.getRecords());
        result.put("pageTotal", blogPage.getPages());
        result.put("pageCurrent", blogPage.getCurrent());
        result.put("pagePre", (blogPage.getCurrent() - (blogPage.hasPrevious() ? 1 : 0)));
        result.put("pageNext", (blogPage.getCurrent() + (blogPage.hasNext() ? 1 : 0)));

        return result;
    }

}
