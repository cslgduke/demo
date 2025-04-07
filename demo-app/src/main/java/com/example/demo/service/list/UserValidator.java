package com.example.demo.service.list;

import com.alibaba.fastjson.JSON;
import com.example.demo.bo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author i565244
 */
@Component
@Slf4j
public class UserValidator implements Validator<User>{

    @Override
    public boolean validate(User user) {
        log.info("UserValidator validator:{}", JSON.toJSONString(user));
        return false;
    }

    @Override
    public String validationType() {
        return "userValidate";
    }
}
