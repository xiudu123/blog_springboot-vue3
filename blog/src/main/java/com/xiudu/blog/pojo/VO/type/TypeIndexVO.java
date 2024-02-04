package com.xiudu.blog.pojo.VO.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: 锈渎
 * @date: 2024/2/1 21:28
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于展示 分类页 的标签
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TypeIndexVO {
    private Long id;
    private String name;
    private Long count;
}
