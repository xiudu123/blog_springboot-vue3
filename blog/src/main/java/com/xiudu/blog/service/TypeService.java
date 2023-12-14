package com.xiudu.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.pojo.Type;

import java.util.List;

/**
 * Created by 锈渎 on 2023/12/13 18:36
 */
public interface TypeService {
    int insertType(Type type);

    int deleteType(Long id);

    int updateType(Long id, Type type);

    Type getType(Long id);
    Page<Type> listType(Integer pageNum);
    Type getTypeByName(String name);
    List<Type> listTypeAll();



}
