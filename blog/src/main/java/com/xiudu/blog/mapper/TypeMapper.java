package com.xiudu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiudu.blog.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by 锈渎 on 2023/12/13 15:21
 */

@Mapper
public interface TypeMapper extends BaseMapper<Type> {
    @Select("SELECT * FROM type WHERE name = #{name}")
    Type selectByName(@Param("name") String name);

    @Select("SELECT COUNT(*) FROM type WHERE id = #{typeId}")
    Long selectCountByTypeId(@Param("typeId") Long typeId);

}
