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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /**
     *
     * @param name 标签名字
     * @return 判断标签名字是否cunz
     * @sql SELECT COUNT(*) FROM type WHERE name = {name}
     */
    @Override
    public Boolean isEmptyByTypeName(String name) {
        return typeMapper.selectCountByTypeName(name) == 0;
    }

    @Override
    public Type getType(Long id) {
        return typeMapper.selectById(id);
    }

    // TODO Limit深分页优化
    /**
     *
     * @param pageNum 分页的当前页
     * @param typeName 标签的名字
     * @return 标签列表
     * @description: 标签分页查询
     */
    @Override
    public Map<String, Object> listTypeAndSearch(Integer pageNum, String typeName) {
        // 查询条件
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        if(typeName != null && !"".equals(typeName)) queryWrapper.like("name", typeName);


        if(pageNum <= 0) pageNum = 1;
        Page<Type> page = new Page<>(pageNum, 10);

        Page<Type> typePage = typeMapper.selectPage(page, queryWrapper);

        if(typePage.getCurrent() > typePage.getPages()) {
            typePage = typeMapper.selectPage(new Page<>(typePage.getPages(), 10), queryWrapper);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("records", typePage.getRecords());
        result.put("pageCurrent", typePage.getCurrent());
        result.put("pageTotal", typePage.getPages());
        result.put("pagePre", (typePage.getCurrent() - (typePage.hasPrevious() ? 1 : 0)));
        result.put("pageNext", (typePage.getCurrent() + (typePage.hasNext() ? 1 : 0)));

        return result;
    }

    /**
     * @return 所有标签
     * @description: 查询标签的博客数量时用到了sql
     * @sql
     * SELECT *, (SELECT COUNT(*) FROM blog WHERE blog_id = type_id AND published = 1) FROM type;
     */
    @Override
    public List<Type> listTypeAll() {
        List<Type> types = typeMapper.selectList(null);
        for(Type type : types) {
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type_id", type.getId()).eq("published", "1");
            type.setCount(blogMapper.selectCount(queryWrapper));
        }
        return types;
    }


}
