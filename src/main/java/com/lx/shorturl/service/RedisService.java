package com.lx.shorturl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import sun.awt.geom.AreaOp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
     * TimeUnit.SECONDS设置时间单位为秒
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

    public Long increment(String key,Long expire){
        Long result=null;
        try {
            ValueOperations ops = redisTemplate.opsForValue();
            result=ops.increment(key);
            if (expire != null){
                redisTemplate.expire(key,expire,TimeUnit.SECONDS);
            }

        }catch (Exception e){
            log.error("写入redis缓存对象失败！错误信息为："+e.getMessage());
        }
        return result;
    }

    public Long incrBy(String key,Long value){
        Long result=null;
        ValueOperations ops = redisTemplate.opsForValue();
        return ops.increment(key, value);

    }


    public void scan(String pattern, Consumer<byte[]> consumer) {

        redisTemplate.execute((RedisConnection connection)->{
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    public List<String> keys(String pattern) {
        List<String> keys = new ArrayList<>();
        this.scan(pattern, item -> {
            //符合条件的key
            String key = new String(item, StandardCharsets.UTF_8);
            keys.add(key);
        });
        return keys;
    }

    /**
     * 不设置缓存时间
     * @param key
     * @param map
     * @return
     */
    public Boolean hset(String key,Map<?,?> map ) {


        boolean result = false;
        try {
            HashOperations ops = redisTemplate.opsForHash();
            ops.putAll(key,map);
            result = true;
        } catch (Exception e) {
            log.error("写入redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    public Boolean hset(String key,Map<?,?> map,Long expire){
        Boolean result=false;
        try {
            HashOperations ops = redisTemplate.opsForHash();
            ops.putAll(key,map);
            redisTemplate.expire(key,expire,TimeUnit.SECONDS);
            result=true;
        }catch (Exception e){
            log.error("写入redis Hash缓存（设置expire存活时间）失败！错误信息为："+e.getMessage());
        }
        return result;
    }

    public String hget(String key ,String field){
        String result=null;
        try {
            HashOperations ops = redisTemplate.opsForHash();
            result = (String)ops.get(key, field);
            return result;
        }catch (Exception e){
            log.error("读取redis缓存失败！错误信息为: "+e.getMessage());
        }
        return result;
    }

    public Long hincr(String key,String field,Long value){
        Long result=null;
        try{
            HashOperations ops = redisTemplate.opsForHash();


            result=ops.increment(key,field,value);
            return result;

        }catch (Exception e){
            log.error("hash 自增失败");
        }
        return result;
    }
}
