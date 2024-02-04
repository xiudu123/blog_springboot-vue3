package com.xiudu.blog.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: 锈渎
 * @date: 2024/1/30 15:28
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BlogContent {
    private Long id; // 跟 blog 表 id 一致
    private String contentHtml; // html内容
    private String contentMarkdown; // markdown内容
}
