package com.xiudu.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.config.api.ResultStatus;
import com.xiudu.blog.config.handler.CustomException;
import com.xiudu.blog.mapper.TypeMapper;
import com.xiudu.blog.pojo.DO.Type;
import com.xiudu.blog.pojo.VO.type.TypeIndexVO;
import com.xiudu.blog.service.TypeService;
import com.xiudu.blog.util.Singleton.TypeSingletonHungry;
import com.xiudu.blog.util.page.PageInfo;
import com.xiudu.blog.util.page.Paging;
import com.xiudu.blog.util.redis.CacheClient;
import com.xiudu.blog.util.redis.CounterClient;
import com.xiudu.blog.util.redis.RedisConstant;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: 锈渎
 * @date: 2023/12/13 18:38
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private CacheClient cacheClient;
    @Autowired
    private CounterClient counterClient;

    /**
     *
     * @param type 添加的类型
     * @return 0 - 添加失败， 1 - 添加成功
     */
    @Transactional
    @Override
    public int insertType(Type type) {
        Date date = new Date();
        type.setCreateTime(date);
        type.setUpdateTime(date);
        type.setCount(0L);
        return typeMapper.insert(type);
    }

    @Transactional
    @Override
    public int deleteType(Long id) {
        Type type = typeMapper.selectById(id);
        if(type == null) {
            throw new CustomException(ResultStatus.NOT_FOUND_TYPE);
        }
        int deleteSuccess = typeMapper.deleteById(id);
        if(deleteSuccess == 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
            return 0;
        }

        cacheClient.delete(RedisConstant.CACHE_TYPE_KEY + id);
        return 1;
    }

    @Transactional
    @Override
    public int updateType(Type newType) {
        Type oldType = typeMapper.selectById(newType.getId());
        if(oldType == null) {
            throw new CustomException(ResultStatus.NOT_FOUND_TYPE);
        }
        newType.setCreateTime(oldType.getCreateTime())
                .setUpdateTime(new Date());
        int updateSuccess = typeMapper.updateById(newType);

        if(updateSuccess == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
            return 0;
        }
        cacheClient.delete(RedisConstant.CACHE_TYPE_KEY + newType.getId());
        return 1;
    }

    @Override
    public Type getType(Long id) {
        return
                cacheClient.queryWithPassThrough(RedisConstant.CACHE_TYPE_KEY, id, Type.class, typeMapper::selectById, RedisConstant.CACHE_TYPE_TTL, TimeUnit.SECONDS);
    }


    /**
     *
     * @param pageNum 分页的当前页
     * @param typeName 标签的名字
     * @return 标签列表
     * @description: 标签分页查询
     */
    @Override
    public PageInfo<Type> listTypeAndSearch(Integer pageNum, String typeName) {
        // 查询条件
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        if(typeName != null && !"".equals(typeName)) queryWrapper.like("name", typeName);
        // 计算 分类总数
        int typeCount = Math.toIntExact(counterClient.getCount(RedisConstant.COUNTER_TYPE_TOTAL_KEY, typeMapper::selectTypeCount));

        return Paging.paging(pageNum, 10, typeCount, queryWrapper, new QueryWrapper<>(), typeMapper::selectList, Type::getId);

    }

    /**
     * @return 所有标签
     * @description: 查询标签的博客数量时用到了sql
     * @sql
     * SELECT *, (SELECT COUNT(*) FROM blog WHERE blog_id = type_id AND published = 1) FROM type;
     */
    @Override
    public List<TypeIndexVO> listTypeAll() {
        return TypeSingletonHungry.getInstance().TypeToIndex(typeMapper.selectList(null));
    }

}
