package com.xiudu.blog;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
//        System.out.println(new Random().nextInt(10));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println(RandomUtil.randomInt(11));
        System.out.println("123".toString());
        String str = "hhh";
        System.out.println(str.toString());
    }

    @Test
    void test() {
        System.out.println("Hello World");
        System.out.println(new Date());
    }

}
