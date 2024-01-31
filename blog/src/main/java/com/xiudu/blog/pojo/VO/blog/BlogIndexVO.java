package com.xiudu.blog.pojo.VO.blog;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: 锈渎
 * @date: 2024/1/30 16:29
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于展示首页和标签页的博客信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogIndexVO {
    private Long id;
    private String title;
    private String firstPicture;
    private Long views;
    private Boolean top;
    private String overview;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    private String typeName;
    private String username;
}