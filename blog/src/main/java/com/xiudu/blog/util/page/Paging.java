package com.xiudu.blog.util.page;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author: 锈渎
 * @date: 2024/2/7 17:46
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 自定义分页查询 - limit深分页优化
 */
public class Paging {

    /**
     *
     * @param pageNum 查询分页的当前页
     * @param size    分页的每页大小
     * @param count   查询物品的总数
     * @param queryWrapperQuery 查询条件
     * @param queryWrapperSelect 查询字段
     * @param dbFallback 查询数据库的回调函数
     * @param getId 获取物品id的回调函数
     * @return 分页信息
     * @param <T> 查询物品的类
     * @description: 根据查询的条件和查询字段返回查询信息(进行了limit的深分页优化)
     */
    public static <T> PageInfo<T> paging(int pageNum,
                                         int size,
                                         int count,
                                         QueryWrapper<T> queryWrapperQuery,
                                         QueryWrapper<T> queryWrapperSelect,
                                         Function<QueryWrapper<T>, List<T>> dbFallback,
                                         Function<T, Long> getId) {
        // 1. 计算总页数
        if(size <= 1) size = 1;
        int pageTotal = (count + size - 1) / size;
        // 2. 判断 pageNum 是否在区间内
        if(pageNum <= 1) pageNum = Math.min(1, pageTotal);
        if(pageNum >= pageTotal) pageNum = pageTotal;
        List<T> list = new ArrayList<>();

        if(pageTotal != 0) { // 如果查询的数据至少存在一个
            // 3. 设置分页查询的起点和终点
            int start = (pageNum - 1) * 10;
            int end = pageNum * 10;
            // 4. 分页查询 (limit优化)
            // 4.1. 查询数据的 id
            queryWrapperQuery.select("id").last("limit " + start + ", " + (end - start));
            list = dbFallback.apply(queryWrapperQuery);
            List<Long> ids = new ArrayList<>();
            for(T t : list) ids.add(getId.apply(t));
            String idStr = StrUtil.join(",", ids);
            // 4.2. 根据 id 查询数据信息， 并根据 id 顺序返回
            queryWrapperSelect.in("id", ids).last("ORDER BY FIELD(id," + idStr + ")");
            list = dbFallback.apply(queryWrapperSelect);
        }
        // 5. 返回
        return new PageInfo<T>()
                .setRecords(list)
                .setPageTotal(pageTotal)
                .setPageCurrent(pageNum)
                .setPagePre((pageNum == 1) ? pageNum : pageNum - 1)
                .setPageNext((pageNum == pageTotal) ? pageNum : pageNum + 1);
    }

}
