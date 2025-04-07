package com.example.demo.rest;

import cn.hutool.core.util.RandomUtil;
import com.example.demo.service.Userservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author i565244
 */
@RestController
@RequestMapping("/aspect")
@Slf4j
public class AspectController {

    @Autowired
    private Userservice userservice;

    @GetMapping("/test")
    public String test() {
        var randomStr = userservice.randomString(RandomUtil.randomInt());
        return  "success";
    }
}
