package com.xiudu.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 锈渎
 * @date: 2023/12/13 15:06
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: Blog 和 Tag 的中间表(多对多)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogTag {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long blogId; // 博客Id
    private Long tagId; // 标签Id
}
