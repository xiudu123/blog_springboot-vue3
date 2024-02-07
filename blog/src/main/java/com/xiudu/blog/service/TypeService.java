package com.xiudu.blog.service;

import com.xiudu.blog.pojo.DO.Type;
import com.xiudu.blog.pojo.VO.type.TypeIndexVO;
import com.xiudu.blog.util.page.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by 锈渎 on 2023/12/13 18:36
 */
public interface TypeService {
    int insertType(Type type);

    int deleteType(Long id);

    int updateType(Type type);


    Type getType(Long id);
    PageInfo<Type> listTypeAndSearch(Integer pageNum, String typeName);
    List<TypeIndexVO> listTypeAll();



}
