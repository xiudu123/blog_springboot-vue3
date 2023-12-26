package com.xiudu.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: 锈渎
 * @date: 2023/12/13 15:04
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 关联数据库 comment 表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String content; // 评论内容
    private String nickname; // 评论者名称
    private String avatar; // 评论者邮箱
    private String email; // 评论者头像
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime; // 评论时间

    private Long parentId; // 父级评论Id
    private Long topCommentId; // 层级评论Id
    private Long blogId; // 所属博客Id
    private Long userId; // 评论者Id, 游客是0
}
