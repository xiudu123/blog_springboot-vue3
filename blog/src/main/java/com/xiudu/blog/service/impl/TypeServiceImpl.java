package com.xiudu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.config.handler.CustomException;
import com.xiudu.blog.mapper.BlogMapper;
import com.xiudu.blog.mapper.TypeMapper;
import com.xiudu.blog.pojo.Blog;
import com.xiudu.blog.pojo.Type;
import com.xiudu.blog.service.TypeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private BlogMapper blogMapper;

    @Transactional
    @Override
    public int insertType(Type type) {
        return typeMapper.insert(type);
    }

    @Transactional
    @Override
    public int deleteType(Long id) {
        Type type = typeMapper.selectById(id);
        if(type == null) {
            throw new CustomException(404, "该类型不存在或已被删除");
        }
        return typeMapper.deleteById(id);
    }

    @Transactional
    @Override
    public int updateType(Long id, Type newType) {
        Type oldType = typeMapper.selectById(id);
        if(oldType == null) {
            throw new CustomException(404, "该类型不存在或已被删除");
        }
        newType.setId(id);
        return typeMapper.updateById(newType);
    }


    @Override
    public Type getType(Long id) {
        return typeMapper.selectById(id);
    }

    @Override
    public Page<Type> listType(Integer pageNum) {
        Page<Type> page = new Page<>(pageNum, 10);
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return typeMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.selectByName(name);
    }

    @Override
    public List<Type> listTypeAll() {
        List<Type> types = typeMapper.selectList(null);
        for(Type type : types) {
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type_id", type.getId());
            type.setCount((long) Math.toIntExact(blogMapper.selectCount(queryWrapper)));
        }
        return types;
    }


}
