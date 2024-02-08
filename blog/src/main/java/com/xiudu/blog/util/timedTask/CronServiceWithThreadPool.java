package com.xiudu.blog.util.timedTask;

import com.alibaba.fastjson.JSON;
import com.xiudu.blog.mapper.BlogMapper;
import com.xiudu.blog.util.redis.CacheClient;
import com.xiudu.blog.util.redis.RedisConstant;
import com.xiudu.blog.util.redis.TimerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author: 锈渎
 * @date: 2024/2/8 17:44
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 定时器，用于完成定时任务。 利用线程池使每个任务互不干预
 */
@Service
@EnableScheduling
public class CronServiceWithThreadPool implements SchedulingConfigurer {

    private final TimerClient timerClient;
    private final StringRedisTemplate stringRedisTemplate;
    private final BlogMapper blogMapper;

    public CronServiceWithThreadPool(TimerClient timerClient, StringRedisTemplate stringRedisTemplate, BlogMapper blogMapper) {
        this.timerClient = timerClient;
        this.stringRedisTemplate = stringRedisTemplate;
        this.blogMapper = blogMapper;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) { // 线程池，有几个定时任务，线程池就开多大
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(1));
    }

    @Scheduled(cron ="0 0 0 ? * MON") // 每周一的0时0分0秒更新
    public void UpdateBlogView() {
        System.out.println("UTC+8时间: " + new Date());
        System.out.println("定时从redis更新博客浏览量数据到mysql");
        List<Long> blogIds = timerClient.getSet(RedisConstant.TIMED_TASK_BLOG_VIEW);
        for(Long blogId : blogIds) {
            String json = stringRedisTemplate.opsForValue().get(RedisConstant.COUNTER_BLOG_VIEW_KEY + blogId);
            if(json == null) continue;
            Long blogView = JSON.parseObject(json, Long.class);
            blogMapper.updateViewById(blogId, blogView);
        }
    }

}
