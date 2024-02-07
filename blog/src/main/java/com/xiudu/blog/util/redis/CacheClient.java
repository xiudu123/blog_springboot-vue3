package com.xiudu.blog.util.redis;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author: 锈渎
 * @date: 2024/2/6 14:28
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于 redis 实现缓存
 */
@Slf4j
@Component
public class CacheClient {

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);
    private final StringRedisTemplate stringRedisTemplate;
    public CacheClient(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
    public void delete(List<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    /**
     *
     * @param key redisKey
     * @param value redisValue
     * @param time 过期时间
     * @param unit 时间单位
     * @description: 将任意Java对象序列化为json并存储在string类型的key中，并且可以设置TTL过期时间
     */
    public void set(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value), time, unit);
    }

    /**
     *
     * @param key redisKey
     * @param value redisValue
     * @param time 过期时间
     * @param unit 时间单位
     * @description: 将任意Java对象序列化为json并存储在string类型的key中，并且可以设置逻辑过期时间，
     * 用于处理缓存击穿问题
     */
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        // 设置逻辑过期时间
        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        // 写入 redis
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(redisData));
    }

    /**
     *
     * @param keyPrefix redisKey前缀
     * @param id 查询对象Id
     * @param type 查询对象类型(Class)
     * @param dbFallback 查询失败后的数据库回调函数
     * @param time 缓存时间
     * @param unit 时间单位
     * @return 查询的对象
     * @param <R> 对象泛型
     * @param <ID> 对象Id泛型
     * @description: 根据指定的key查询缓存，并反序列化为指定类型，利用缓存空值的方式解决缓存穿透问题
     */
    public <R, ID> R queryWithPassThrough(
            String keyPrefix, ID id,
            Class<R> type,
            Function<ID, R> dbFallback,
            Long time, TimeUnit unit) {
        String key = keyPrefix + id;
        // 1. 从 redis 查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2. 判断是否存在
        if(StrUtil.isNotBlank(json)) {
            // 存在则直接返回
            return JSON.parseObject(json, type);
        }

        // 3. 判断命中是否未空值
        if(json != null) {
            // 返回错误信息
            return null;
        }

        // 4. 缓存里不存在, 根据 id 查询数据库
        R r = dbFallback.apply(id);

        // 5. 数据库里不存在，返回错误信息，并缓存空值
        if(r == null) {
            stringRedisTemplate.opsForValue().set(key, "", RedisConstant.CACHE_NULL_TTL, TimeUnit.SECONDS);
            return null;
        }
        // 6. 存在写入redis
        this.set(key, r, time, unit);
        return r;
    }

    /**
     * 通常为热点数据，查询前需要提前将数据缓存在redis中
     * @param keyPrefix redisKey前缀
     * @param id 查询对象Id
     * @param type 查询对象类型(class)
     * @param dbFallback 查询失败后的数据库回调函数
     * @param time 过期时间
     * @param unit 时间单位
     * @return 查询的对象
     * @param <R> 对象泛型
     * @param <ID> 对象id泛型
     * @description: 根据指定的key查询缓存，并反序列化为指定类型，需要利用逻辑过期 + 互斥锁 + 线程池解决缓存击穿问题
     */
    public <R, ID> R queryWithLogicalExpire(
            String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
        String key = keyPrefix + id;
        // 1. 从 redis 查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2. 判断是否存在
        if(StrUtil.isBlank(json)) {
            // 不存在
            return null;
        }
        // 3. 缓存命中，将 json 反序列化为对象
        RedisData redisData = JSON.parseObject(json, RedisData.class);
        R r = JSON.parseObject(redisData.getData().toString(), type);
        LocalDateTime expireTime = redisData.getExpireTime();

        // 4. 判断是否过期
        if(expireTime.isAfter(LocalDateTime.now())) {
            // 4.1. 未过期，直接返回
            return r;
        }
        // 4.2. 已过期，重建缓存
        // 6. 缓存重建
        // 6.1. 获取互斥锁
        String lockKey = keyPrefix + "lock:" + id;
        boolean isLock = tryLock(lockKey);
        // 6.2. 判断是否获取锁成功
        if(isLock) {
            // 成功，开启独立线程，实现缓存重建
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    // 查询数据库
                    R newR = dbFallback.apply(id);
                    // 重建缓存
                    this.setWithLogicalExpire(key, newR, time, unit);
                }finally {
                    // 释放锁
                    unlock(lockKey);
                }
            });
        }
        // 7. 返回过期的数据信息
        return r;
    }

    private boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }

}
