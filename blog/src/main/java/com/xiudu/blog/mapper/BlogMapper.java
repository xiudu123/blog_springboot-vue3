package com.xiudu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiudu.blog.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * Created by 锈渎 on 2023/12/13 15:19
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    @Update("UPDATE blog SET views = views+1 WHERE id = #{id}")
    void updateViewById(Long id); // 浏览次数 ++;
}
