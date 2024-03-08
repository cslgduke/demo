package com.example.demo.rest;

import cn.amorou.uid.UidGenerator;
import cn.hutool.core.util.RandomUtil;
import com.example.demo.bo.User;
import com.example.demo.core.CustomThreadFactory;
import com.example.demo.msg.KafkaProducer;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.Userservice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.Response;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
