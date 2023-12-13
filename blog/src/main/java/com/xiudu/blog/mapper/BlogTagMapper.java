package com.xiudu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiudu.blog.pojo.BlogTag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 锈渎 on 2023/12/13 15:19
 */
@Mapper
public interface BlogTagMapper extends BaseMapper<BlogTag> {

    @Delete("DELETE FROM blog_tag WHERE blog_id = #{blog_id}")
    void deleteTagByBlogId(@Param("blog_id") Long blogId);

    @Select("SELECT tag_id FROM blog_tag WHERE blog_id = #{blog_id}")
    List<Long> listTagIdsByBlogId(@Param("blog_id")Long blogId);
}
