package com.xiudu.blog.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.xiudu.blog.mapper.UserMapper;
import com.xiudu.blog.pojo.DO.User;
import com.xiudu.blog.service.UserService;
import com.xiudu.blog.util.redis.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: 锈渎
 * @date: 2023/12/13 18:38
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
       return userMapper.selectByUsernameAndPassword(username, password);
    }

    /**
     * @param userId 用户id
     * @return 用户
     * @sql SELECT * FROM user WHERE id = {userId}
     * @redis: 查询用户缓存
     */
    @Override
    public User selectUserById(Long userId) {
        String redisUser = stringRedisTemplate.opsForValue().get(RedisConstant.CACHE_USER_KEY + userId);
        if(redisUser != null) {
            return JSON.parseObject(redisUser, User.class);
        }
        User user = userMapper.selectById(userId);
        user.setPassword("");
        stringRedisTemplate.opsForValue().set(RedisConstant.CACHE_USER_KEY + userId, JSON.toJSONString(user), RedisConstant.CACHE_USER_TTL + RandomUtil.randomLong(600), TimeUnit.SECONDS);
        return user;
    }
}
