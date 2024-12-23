package com.example.demo.rest;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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

    @Autowired
    private RedissonClient redissonClient;



    @PostMapping("/redisGet/{key}")
    public Object redisGet(@PathVariable String key) {
        var value = "";
//        if(redisTemplate.hasKey(key)){
//           value  = (String) redisTemplate.opsForValue().get(key);
//        }
        return value;
    }

    @PostMapping("/redisSet/{key}")
    public Object redisSet(@PathVariable String key) {
//        redisTemplate.opsForValue().set(key,RandomUtil.randomString(10));
//        redisTemplate.opsForValue().set(key+"_ttl",RandomUtil.randomString(10),RandomUtil.randomInt(10,20), TimeUnit.SECONDS);


        RLock lock = redissonClient.getLock(key + "_redisson");
        if(lock.tryLock()){
            log.info("acquired lock");
        }

//        redissonClient.getSpinLock(key + "_redisson").lock();
//        lock.tryLock(-1,-1,TimeUnit.SECONDS);
        return "success";
    }



}
