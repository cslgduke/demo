package com.example.demo.rest;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author i565244
 */
@RestController("/redis")
@Slf4j
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping("/redisGet/{key}")
    public Object redisGet(@PathVariable String key) {
        var value = "";
        if(redisTemplate.hasKey(key)){
           value  = (String) redisTemplate.opsForValue().get(key);
        }
        return value;
    }

    @PostMapping("/redisSet/{key}")
    public Object redisSet(@PathVariable String key) {
        redisTemplate.opsForValue().set(key,RandomUtil.randomString(10));
        return "success";
    }



}
