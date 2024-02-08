package com.xiudu.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiudu.blog.config.api.ResultStatus;
import com.xiudu.blog.config.handler.CustomException;
import com.xiudu.blog.mapper.*;
import com.xiudu.blog.pojo.DO.Blog;
import com.xiudu.blog.pojo.DTO.blog.BlogDTO;
import com.xiudu.blog.pojo.VO.blog.BlogArchiveVO;
import com.xiudu.blog.pojo.VO.blog.BlogFooterVO;
import com.xiudu.blog.pojo.VO.blog.BlogIndexVO;
import com.xiudu.blog.pojo.VO.blog.BlogViewVO;
import com.xiudu.blog.pojo.VO.blog.admin.BlogAdminUpdateVO;
import com.xiudu.blog.pojo.VO.blog.admin.BlogAdminVO;
import com.xiudu.blog.service.BlogService;
import com.xiudu.blog.util.Singleton.BlogSingletonHungry;
import com.xiudu.blog.util.page.PageInfo;
import com.xiudu.blog.util.page.Paging;
import com.xiudu.blog.util.redis.CacheClient;
import com.xiudu.blog.util.redis.CounterClient;
import com.xiudu.blog.util.redis.RedisConstant;
import com.xiudu.blog.util.redis.TimerClient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CacheClient cacheClient;
    @Autowired
    private CounterClient counterClient;
    @Autowired
    private TimerClient timerClient;

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
                .setTypeId(blogDTO.getTypeId())
                .setContentHtml(blogDTO.getContentHtml())
                .setContentMarkdown(blogDTO.getContentMarkdown());
        int insertSuccess = blogMapper.insert(blog);

        if(insertSuccess == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
            return 0;
        }

        if(blogDTO.getPublished()) {
            Long typeId = blogDTO.getTypeId();
            typeMapper.addCountById(typeId);
            cacheClient.delete(RedisConstant.CACHE_TYPE_KEY + typeId);
            cacheClient.delete(RedisConstant.CACHE_BLOG_TOP_KEY);
            counterClient.addCount(RedisConstant.COUNTER_BLOG_TOTAL_KEY, blogMapper::selectBlogCount);
        }

        return 1;
    }

    /**
     * @param blogId 博客Id
     * @return 0 - 删除成功, 1 - 删除失败
     */
    @Transactional
    @Override
    public int deleteBlog(Long blogId, Long typeId, Boolean published) {
        typeMapper.deleteCountById(typeId);
        int deleteSuccess = blogMapper.deleteById(blogId);

        if(deleteSuccess == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
            return 0;
        }

        if(published) {
            cacheClient.delete(RedisConstant.CACHE_BLOG_INFO_KEY + blogId);
            cacheClient.delete(RedisConstant.CACHE_TYPE_KEY + typeId);
            counterClient.delCount(RedisConstant.COUNTER_BLOG_TOTAL_KEY, blogMapper::selectBlogCount);
            cacheClient.delete(RedisConstant.CACHE_BLOG_TOP_KEY);
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
                .setViews(oldBlog.getViews())
                .setContentHtml(newBlogDTO.getContentHtml())
                .setContentMarkdown(newBlogDTO.getContentMarkdown());
        int updateSuccess = blogMapper.updateById(blog);
        if(updateSuccess == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
            return 0;
        }


        List<String> keys = new ArrayList<>();
        if(oldBlog.getPublished()) {
            Long oldTypeId = oldBlog.getTypeId();
            typeMapper.deleteCountById(oldTypeId);
            keys.add(RedisConstant.CACHE_TYPE_KEY + oldTypeId);
            counterClient.delCount(RedisConstant.COUNTER_BLOG_TOTAL_KEY, blogMapper::selectBlogCount);
        }
        if(newBlogDTO.getPublished()) {
            Long newTypeId = newBlogDTO.getTypeId();
            typeMapper.addCountById(newTypeId);
            if(keys.isEmpty() || !(Objects.equals(oldBlog.getTypeId(), newTypeId))) {
                keys.add(RedisConstant.CACHE_TYPE_KEY + newTypeId);
            }
            counterClient.addCount(RedisConstant.COUNTER_BLOG_TOTAL_KEY, blogMapper::selectBlogCount);
        }
        keys.add(RedisConstant.CACHE_BLOG_INFO_KEY + oldBlog.getId());
        cacheClient.delete(keys);
        cacheClient.delete(RedisConstant.CACHE_BLOG_TOP_KEY);
        return 1;
    }

    /**
     *
     * @param blogId 博客Id
     * @return 根据博客Id返回的博客
     */
    @Override
    public Blog getBlog(Long blogId) {

        Blog blog = cacheClient.queryWithPassThrough(RedisConstant.CACHE_BLOG_INFO_KEY, blogId, Blog.class, blogMapper::selectById, RedisConstant.CACHE_BLOG_INFO_TTL, TimeUnit.SECONDS);

        if(blog == null) throw new CustomException(ResultStatus.NOT_FOUND_BLOG);

        return blog;
    }

    /**
     *
     * @param pageNum 分页当前页
     * @return 博客列表
     * @description: 首页的博客分页列表
     */
    @Override
    public PageInfo<BlogIndexVO> listBlogIndex(Integer pageNum) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "create_time");

        return getBlogIndexPage(pageNum, queryWrapper);
    }

    // TODO 首页搜索
    @Override
    public PageInfo<BlogIndexVO> listBlogSearch(Integer pageNum, String query) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "create_time");
        queryWrapper.like("title", query);
        return getBlogIndexPage(pageNum, queryWrapper);
    }

    /**
     * @param pageNum 分页当前页
     * @param typeId 标签Id
     * @return 根据标签Id返回的博客列表
     */
    @Override
    public PageInfo<BlogIndexVO> listBlogByTypedId(Integer pageNum, Long typeId) {

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top");
        queryWrapper.orderByDesc("create_time");
        if(typeMapper.selectCountByTypeId(typeId) > 0) queryWrapper.eq("type_id", typeId);


        return getBlogIndexPage(pageNum, queryWrapper);
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
     * LIMIT {size}<br>
     * @redis: 作为永不过期的缓存
     */
    @Override
    public List<BlogFooterVO> listTop(Long size) {
        String blogTopKey = RedisConstant.CACHE_BLOG_TOP_KEY;
        String redisBlogTop = stringRedisTemplate.opsForValue().get(blogTopKey);
        if(redisBlogTop != null) {
            return JSON.parseArray(redisBlogTop, BlogFooterVO.class);
        }

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "title")
                .eq("published", 1)
                .orderByDesc( "create_time")
                .last("LIMIT " + size.toString());
        List<Blog> blogs = blogMapper.selectList(queryWrapper);

        List<BlogFooterVO> blogFooterVOS = BlogSingletonHungry.getInstance().BlogToFooter(blogs);
        stringRedisTemplate.opsForValue().set(blogTopKey, JSON.toJSONString(blogFooterVOS));
        return blogFooterVOS;
    }

    /**
     *
     * @return 博客总数
     * @sql SELECT COUNT(*) FROM blog  WHERE published = 1
     * @redis: 用作 blog 计数器
     */
    @Override
    public Long blogCount() {

        return counterClient.getCount(RedisConstant.COUNTER_BLOG_TOTAL_KEY, blogMapper::selectBlogCount);
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
        Long blogView = counterClient.addCount(RedisConstant.COUNTER_BLOG_VIEW_KEY + blogId, blog::getViews);
        blog.setViews(blogView);
        timerClient.setSet(RedisConstant.TIMED_TASK_BLOG_VIEW, blogId);
//        blogMapper.addViewById(blogId);
//        blog.setViews(blog.getViews() + 1);
        return BlogSingletonHungry.getInstance().BlogToView(blog, typeMapper, userMapper, cacheClient);
    }

    @Override
    public BlogAdminUpdateVO getBlogUpdate(Long blogId) {
        Blog blog = getBlog(blogId);
        return BlogSingletonHungry.getInstance().BlogToUpdate(blog, typeMapper, cacheClient);
    }


    /**
     * @param pageNum 分页当前页
     * @param query 查询条件
     * @return 根据查询条件返回的博客列表
     */
    @Override
    public PageInfo<BlogAdminVO> listBlogAdminQuery(Integer pageNum, Map<String, String> query) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc( "create_time");

        if(!("-1".equals(query.get("typeId"))))    queryWrapper.eq("type_id", query.get("typeId"));
        if(!("-1".equals(query.get("top"))))       queryWrapper.eq("top", query.get("top"));
        if(!("-1".equals(query.get("published")))) queryWrapper.eq("published", query.get("published"));
        if(!("-1".equals(query.get("comment"))))   queryWrapper.eq("comment", query.get("comment"));
        if(!("".equals(query.get("title"))))       queryWrapper.like("title", query.get("title"));

        return getBlogAdminPage(pageNum, queryWrapper);
    }

    /**
     * @param pageNum 分页的当前页
     * @param queryWrapperQuery 查询条件
     * @return 博客列表
     * @description: 博客分页查询 <br>
     * 1 - 首页 博客列表 <br>
     * 2 - 根据分类 博客列表 <br>
     * 3 - 管理员搜索 博客列表 <br>
     */
    private PageInfo<BlogIndexVO> getBlogIndexPage(Integer pageNum, QueryWrapper<Blog> queryWrapperQuery) {
        // 计算博客总数
        int blogCount = Math.toIntExact(counterClient.getCount(RedisConstant.COUNTER_BLOG_TOTAL_KEY, blogMapper::selectBlogCount));
        // 需要要查询的字段
        QueryWrapper<Blog> queryWrapperSelect = new QueryWrapper<Blog>()
                .select("id", "title", "first_picture", "views", "top", "overview", "create_time", "type_id", "user_id");
        // 分页查询
        PageInfo<Blog> blogPageInfo =
                Paging.paging(pageNum, 10, blogCount, queryWrapperQuery, queryWrapperSelect, blogMapper::selectList, Blog::getId);
        // 返回
        return new PageInfo<BlogIndexVO>()
                .setPageTotal(blogPageInfo.getPageTotal())
                .setPageCurrent(blogPageInfo.getPageCurrent())
                .setPagePre(blogPageInfo.getPagePre())
                .setPageNext(blogPageInfo.getPageNext())
                .setRecords(BlogSingletonHungry.getInstance().BlogToIndex(blogPageInfo.getRecords(), typeMapper, userMapper, cacheClient));
    }

    private PageInfo<BlogAdminVO> getBlogAdminPage(Integer pageNum, QueryWrapper<Blog> queryWrapperQuery) {
        // 计算博客总数
        int blogCount = Math.toIntExact(counterClient.getCount(RedisConstant.COUNTER_BLOG_TOTAL_KEY, blogMapper::selectBlogCount));
        // 需要查询的字段
        QueryWrapper<Blog> queryWrapperSelect = new QueryWrapper<Blog>()
                .select("id", "title", "type_id", "top", "published", "comment", "views", "create_time", "update_time");
        // 分页查询
        PageInfo<Blog> blogPageInfo =
                Paging.paging(pageNum, 10, blogCount, queryWrapperQuery, queryWrapperSelect, blogMapper::selectList, Blog::getId);
        // 返回
        return new PageInfo<BlogAdminVO>()
                .setPageTotal(blogPageInfo.getPageTotal())
                .setPageCurrent(blogPageInfo.getPageCurrent())
                .setPagePre(blogPageInfo.getPagePre())
                .setPageNext(blogPageInfo.getPageNext())
                .setRecords(BlogSingletonHungry.getInstance().BlogToAdmin(blogPageInfo.getRecords(), typeMapper, cacheClient));
    }

}
