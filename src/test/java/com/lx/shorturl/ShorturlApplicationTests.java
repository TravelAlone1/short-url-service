package com.lx.shorturl;

import com.lx.shorturl.entity.User;
import com.lx.shorturl.service.RedisService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShorturlApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
        stringRedisTemplate.opsForValue().set("aaa","111");
        System.out.println("success redis");
        Assert.assertEquals("111",stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testRedis(){
//      iRedisService.set("aaa","BBB");
        redisTemplate.opsForValue().set("user1",new User(1,"yj","123","111"));
        User user1 = (User)redisTemplate.opsForValue().get("user1");
        System.out.println(user1);
    }

    @Test
    public void testHash(){
        String s = redisService.hget("201909282234","j6RRze");
        System.out.println(s);
    }





}
