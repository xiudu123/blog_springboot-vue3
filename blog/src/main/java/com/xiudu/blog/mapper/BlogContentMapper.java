package com.xiudu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiudu.blog.pojo.BlogContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by 锈渎 on 2024/1/30 19:25
 */
@Mapper
public interface BlogContentMapper extends BaseMapper<BlogContent> {



    @Select("SELECT content_html FROM blog_content WHERE id = #{blogId}")
    String selectHtmlById(Long blogId);

    @Select("SELECT content_markdown FROM blog_content WHERE id = #{blogId}")
    String selectMarkdownById(Long blogId);
}
