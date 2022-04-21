package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;

/**
 * @author i565244
 */
@Slf4j
public class UserDaoImpl {

    public int addUser(String user) {
        log.info("save user:{}", user);
        return 1;

    }

    public int deleteUser(int id) {
        if (id <= 0 || id > 99999999) {
            throw new IllegalArgumentException("参数id不能大于99999999或小于0");
        }
        log.info("remove user:{}",id);
        return 1;
    }
}
