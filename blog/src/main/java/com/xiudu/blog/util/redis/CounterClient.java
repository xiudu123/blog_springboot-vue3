package com.xiudu.blog.util.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * @author: 锈渎
 * @date: 2024/2/6 20:40
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于 redis 作为计数器
 */
@Slf4j
@Component
public class CounterClient {
    private final StringRedisTemplate stringRedisTemplate;

    public CounterClient(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     *
     * @param key 计数器的key
     * @param dbFallback 缓存查询失败后的数据库回调函数
     * @return 计数器
     * @description: 获取计数器
     */
    public Long getCount(String key, Supplier<Long> dbFallback) {
        // 1. 从 redis 查询数据
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2. 判断是否存在
        if(json != null) {
            // 存在则直接返回
            return JSON.parseObject(json, Long.class);
        }
        // 3. 缓存不存在，直接查询数据库统计数量
        Long count = dbFallback.get();
            //不存在则赋值为0
        if(count == null) count = 0L;
        // 4. 存入缓存
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(count));
        // 5. 返回
        return count;
    }

    /**
     *
     * @param key 计数器的key
     * @param dbFallback 缓存查询失败后的数据库回调函数
     * @return 计数器
     * @description: 计数器加1
     */
    public Long addCount(String key, Supplier<Long> dbFallback) {
        // 1. 从 redis 查询数据
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2. 判断是否存在
        if(json != null) {
            // 存在则个数自加1, 并返回
            stringRedisTemplate.opsForValue().increment(key, 1);
            return JSON.parseObject(json, Long.class) + 1;
        }
        // 3. 缓存不存在, 则直接查询数据库，并使数量加1
        Long count = dbFallback.get();
            //不存在则赋值为0
        if(count == null) count = 0L;
        count += 1;
        // 4. 存入缓存
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(count));
        // 5. 返回
        return count;
    }

    /* 重载添加计数 */
    public<ID> Long addCount(String keyPrefix, ID id, Function<ID, Long> dbFallback) {
        String key = keyPrefix + id;
        // 1. 从 redis 查询数据
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2. 判断是否存在
        if(json != null) {
            // 存在则个数自加1, 并返回
            stringRedisTemplate.opsForValue().increment(key, 1);
            return JSON.parseObject(json, Long.class) + 1;
        }
        // 3. 缓存不存在, 则直接查询数据库，并使数量加1
        Long count = dbFallback.apply(id);
            //不存在则赋值为0
        if(count == null) count = 0L;
        count += 1L;
        // 4. 存入缓存
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(count));
        // 5. 返回
        return count;
    }


    /**
     *
     * @param key 计数器的key
     * @param dbFallback 缓存查询失败后的数据库回调函数
     * @return 计数器
     * @description: 计数器减1
     */
    public Long delCount(String key, Supplier<Long> dbFallback) {
        // 1. 从 redis 查询数据
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2. 判断是否存在
        if (json != null) {
            // 存在则个数自减1, 并返回
            stringRedisTemplate.opsForValue().increment(key, -1);
            return JSON.parseObject(json, Long.class) - 1;
        }
        // 3. 缓存不存在，则直接查询数据库, 并使数量减1
        Long count = dbFallback.get();
            //不存在则赋值为0
        if(count == null) count = 0L;
        count -= 1L;
        // 4. 存入缓存
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(count));
        // 5. 返回
        return count;
    }

    /*重载减少计数*/
    public<ID> Long delCount(String keyPrefix, ID id, Function<ID, Long> dbFallback) {
        String key = keyPrefix + id;
        // 1. 从 redis 查询数据
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2. 判断是否存在
        if (json != null) {
            // 存在则个数自减1, 并返回
            stringRedisTemplate.opsForValue().increment(key, -1);
            return JSON.parseObject(json, Long.class) - 1;
        }
        // 3. 缓存不存在，则直接查询数据库, 并使数量减1
        Long count = dbFallback.apply(id);
            //不存在则赋值为0
        if(count == null) count = 0L;
        count -= 1L;
        // 4. 存入缓存
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(count));
        // 5. 返回
        return count;
    }

}
