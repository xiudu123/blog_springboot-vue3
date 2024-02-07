package com.xiudu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiudu.blog.pojo.DO.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by 锈渎 on 2023/12/13 15:21
 */

@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    @Select("SELECT COUNT(*) FROM type WHERE id = #{typeId}")
    Long selectCountByTypeId(@Param("typeId") Long typeId);

    @Select("SELECT COUNT(*) FROM type WHERE name = #{name}")
    Long selectCountByTypeName(@Param("name") String name);

    @Select("SELECT COUNT(*) FROM type")
    Long selectTypeCount();

    @Update("UPDATE type SET count = count + 1 WHERE id = #{id}")
    void addCountById(@Param("id") Long id);

    @Update("UPDATE type SET count = count - 1 WHERE id = #{id}")
    void deleteCountById(@Param("id") Long id);

}
