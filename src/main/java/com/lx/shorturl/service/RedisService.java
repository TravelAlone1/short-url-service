package com.lx.shorturl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: lx
 * @Date: 2019/9/25 21:21
 */
@Service
public class RedisService {

    private Logger log = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 不设置缓存时间
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean set(String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(key, value);
            result = true;
        } catch (Exception e) {
            log.error("写入redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 写入redis缓存（设置expire存活时间）
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public Boolean set(String key, String value, Long expire) {
        boolean result = false;
        try {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(key, value);
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.error("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 读取redis缓存
     *
     * @param key
     * @return
     */
    public String get(String key) {
        String result = null;
        try {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            result = ops.get(key);
        } catch (Exception e) {
            log.error("读取redis缓存失败！错误信息为: " + e.getMessage());
        }
        return result;
    }

    /**
     * 判断redis缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        Boolean result = false;
        try {
            result = stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("判断redis缓存中是否有对应的key失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据key删除对应的value
     *
     * @param key
     * @return
     */
    public Boolean remove(String key) {
        Boolean result = false;
        try {
            if (exists(key)) {
                stringRedisTemplate.delete(key);
            }
            result = true;
        } catch (Exception e) {
            log.error("redis根据key删除对应的value失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据keys批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    public Object getObject(String key){
        ValueOperations ops = redisTemplate.opsForValue();
        Object o = ops.get(key);
        return o;
    }

    public Boolean set(String key,Object value){
        Boolean result =false;
        try {
            ValueOperations ops = redisTemplate.opsForValue();
            ops.set(key,value);
            result=true;
        }catch (Exception e){
            log.error("写入redis缓存对象失败！错误信息为："+e.getMessage());
        }
        return result;
    }

}
