package com.xiudu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.config.handler.CustomException;
import com.xiudu.blog.mapper.TagMapper;
import com.xiudu.blog.pojo.Tag;
import com.xiudu.blog.service.TagService;
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
public class TagServiceImpl implements TagService {


    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public int insertTag(Tag tag) {
        return tagMapper.insert(tag);
    }

    @Transactional
    @Override
    public int deleteTag(Long id) {
        Tag tag = tagMapper.selectById(id);
        if(tag == null) {
            throw new CustomException(404, "该标签不存在或已被删除");
        }
        return tagMapper.deleteById(id);
    }

    @Transactional
    @Override
    public int updateTag(Long id, Tag newTag) {
        Tag oldTag = tagMapper.selectById(id);
        if(oldTag == null) {
            throw new CustomException(404, "该标签不存在或已被删除");
        }
        newTag.setId(id);
        return tagMapper.updateById(newTag);
    }


    @Override
    public Tag getTag(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public Page<Tag> listTag(Integer pageNum) {
        Page<Tag> page = new Page<>(pageNum, 10);
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return tagMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.selectByName(name);
    }

    @Override
    public List<Tag> listTagAll() {
        return tagMapper.selectList(null);
    }


}
