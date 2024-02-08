package com.xiudu.blog.util.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: 锈渎
 * @date: 2024/2/8 20:26
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 用于 redis 存储用于定时更新的数据
 */

@Slf4j
@Component
public class TimerClient {
    private final StringRedisTemplate stringRedisTemplate;

    public TimerClient(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void setSet(String key, Object value) {
        stringRedisTemplate.opsForSet().add(key, JSON.toJSONString(value));
    }

    public List<Long> getSet(String key) {
        Set<String> jsons = stringRedisTemplate.opsForSet().members(key);
        if(jsons == null) return new ArrayList<>();

        stringRedisTemplate.delete(key);
        List<Long> list = new ArrayList<>();
        for(String json : jsons) {
            list.add(JSON.parseObject(json, Long.class));
        }
        return list;
    }

}

