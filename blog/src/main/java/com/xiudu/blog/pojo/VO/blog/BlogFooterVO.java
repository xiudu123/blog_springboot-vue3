package com.xiudu.blog.pojo.VO.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: 锈渎
 * @date: 2024/1/30 15:54
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于展示页脚的博客信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogFooterVO {
    private Long id;
    private String title;
}
