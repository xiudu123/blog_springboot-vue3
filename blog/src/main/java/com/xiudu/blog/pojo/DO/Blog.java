package com.xiudu.blog.pojo.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: 锈渎
 * @date: 2023/12/13 15:07
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Blog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title; // 标题
    private String firstPicture; // 首图
    private Long views; // 浏览次数
    private Boolean top; // 是否置顶
    private Boolean published; // 是否发布
    private Boolean comment; // 是否开启评论
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime; // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime; // 修改时间
    private String overview; // 概述
    private Long userId; // 发布者Id
    private Long typeId; // 类型Id
    private String contentHtml; // html内容
    private String contentMarkdown; // markdown内容
}
