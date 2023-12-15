package com.xiudu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiudu.blog.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by 锈渎 on 2023/12/13 15:19
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    @Update("UPDATE blog SET views = views+1 WHERE id = #{id}")
    void updateViewById(Long id); // 浏览次数 ++;

    @Select("SELECT COUNT(*) FROM blog WHERE published = 1")
    Long selectBlogCount();

    @Select("SELECT COUNT(*) FROM blog WHERE type_id = #{typeId} AND published = 1")
    Long selectBlogCountByTypeId(Long typeId);

}
