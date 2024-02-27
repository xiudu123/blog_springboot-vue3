package com.xiudu.blog.util.limit;

/**
 * @author: 锈渎
 * @date: 2024/2/26 21:15
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于限流类型的枚举内
 */
public enum LimitType {
    DEFAULT, // 默认全局限流
    IP // 根据请求者IP限流
}
