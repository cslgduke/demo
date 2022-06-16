package com.example.demo.service.list;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author i565244
 */
@Component
@Slf4j
public class StringValidator implements Validator<String>{
    @Override
    public boolean validate(String s) {
        log.info("String validator:{}", JSON.toJSONString(s));
        return false;
    }


    @Override
    public String validationType() {
        return "stringValidate";
    }
}
