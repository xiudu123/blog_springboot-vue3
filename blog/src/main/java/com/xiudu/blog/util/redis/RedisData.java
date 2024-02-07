package com.xiudu.blog.util.redis;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: 锈渎
 * @date: 2024/2/6 14:27
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于 redis 储存用于逻辑过期的热点数据
 */
@Data
public class RedisData {
    private LocalDateTime expireTime; // 逻辑过期时间;
    private Object data;
}
