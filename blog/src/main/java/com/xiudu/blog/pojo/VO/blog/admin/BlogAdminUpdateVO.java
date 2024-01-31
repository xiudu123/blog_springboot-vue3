package com.xiudu.blog.pojo.VO.blog.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: 锈渎
 * @date: 2024/1/31 10:44
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于展示修改博客时的博客信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogAdminUpdateVO {
    private Long id;
    private String title;
    private Long typeId;
    private String typeName;
    private String firstPicture;
    private String contentMarkdown;
    private String overview;
    private Boolean top;
    private Boolean comment;
}
