package com.example.demo.service;

import cn.hutool.core.util.RandomUtil;
import com.example.demo.annotation.EntityHandlePoint;
import com.example.demo.bo.User;
import com.example.demo.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author i565244
 */
@Component
@Slf4j
public class Userservice {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CacheManager cacheManager;

    @Transactional(propagation=Propagation.REQUIRED)
    public User save(User user){
      return userRepository.save(user);
    }

    @Cacheable(value="userCache" ,keyGenerator = "cacheKeyCenerator")
    public User findById(Long id){
        var rst = userRepository.findById(id);
        return rst.isPresent() ? rst.get() : null;
    }

    @CacheEvict(value="userCache" ,keyGenerator = "cacheKeyCenerator")
    public User update(User user){
        return userRepository.save(user);
    }


    @EntityHandlePoint
    public String randomString(Integer len){
        var str = RandomUtil.randomString(10);
        log.info("return randomString {}",str);
        return str;
    }

}
