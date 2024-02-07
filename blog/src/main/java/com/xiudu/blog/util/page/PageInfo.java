package com.xiudu.blog.util.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: 锈渎
 * @date: 2024/2/7 17:44
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 分页信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageInfo<T> {
    List<T> records;
    int pageCurrent;
    int pageTotal;
    int pagePre;
    int pageNext;
}
