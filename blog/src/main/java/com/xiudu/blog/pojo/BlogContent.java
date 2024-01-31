package com.xiudu.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private Long id;
    private String contentHtml;
    private String contentMarkdown;
}
