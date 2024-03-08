package com.example.demo.rest;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.example.demo.dto.UserBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author i565244
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Autowired
    private Environment env;

    @RequestMapping("/user")
    public Object env() {
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var user = UserBo.builder().id(RandomUtil.randomNumbers(10))
                .age(RandomUtil.randomInt(20,100))
                .no(RandomUtil.randomString(10)).build();
        log.info("user info:{}", JSON.toJSONString(user));
        return user;
    }
}