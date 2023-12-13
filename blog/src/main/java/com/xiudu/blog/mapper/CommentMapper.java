package com.xiudu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiudu.blog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by 锈渎 on 2023/12/13 15:20
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
