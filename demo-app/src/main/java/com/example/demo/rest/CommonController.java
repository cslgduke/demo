package com.example.demo.rest;

import cn.amorou.uid.UidGenerator;
import cn.hutool.core.util.RandomUtil;
import com.example.demo.bo.User;
import com.example.demo.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author i565244
 */
@RestController
@Slf4j
public class CommonController {

    @Resource
    private UidGenerator uidGenerator;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/uuid")
    public String generateUUid(){
        var uuid = uidGenerator.getUID();
        return String.valueOf(uuid);
    }

    @PostMapping("/userAdd")
    public String userAdd(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName(RandomUtil.randomString(10));
            users.add(user);
        }
        userRepository.saveAll(users);
        return "success";
    }

    @PostMapping("/userList")
    public Object userList(){
        return userRepository.findAll();
    }


    @PostMapping("/userRefine")
    public Object update(){
       var toList =  userRepository.findAll().stream().filter(t -> t.getNo() == null).collect(Collectors.toList());
        log.info("no is null entities:{}",toList);
        userRepository.saveAll(toList);
        return "success";
    }

    @PostMapping("/userRefresh")
    public Object userRefresh(){
        userRepository.refreshUserNo();
        return "success";
    }
}
