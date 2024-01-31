package com.xiudu.blog.pojo.VO.blog.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: 锈渎
 * @date: 2024/1/30 22:03
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于展示管理员界面博客信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogAdminVO {
    private Long id;
    private String title;
    private String typeName;
    private Boolean top;
    private Boolean published;
    private Boolean comment;
    private Long view;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

}
