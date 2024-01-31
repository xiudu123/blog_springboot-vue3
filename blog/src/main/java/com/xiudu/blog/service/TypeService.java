package com.xiudu.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiudu.blog.pojo.Type;

import java.util.List;
import java.util.Map;

/**
 * Created by 锈渎 on 2023/12/13 18:36
 */
public interface TypeService {
    int insertType(Type type);

    int deleteType(Long id);

    int updateType(Long id, Type type);

    Boolean isEmptyByTypeName(String name);
    Type getType(Long id);
    Map<String, Object> listTypeAndSearch(Integer pageNum, String typeName);
    List<Type> listTypeAll();



}
