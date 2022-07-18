package com.example.demo.cache;

import com.example.demo.bo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author i565244
 */
@Slf4j
@Component
public class CacheKeyCenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params.length != 1) {
            throw new IllegalArgumentException("Not supported parameter number of KeyGenerator");
        }
        String subKey;
        if ((params[0] instanceof Long)) {
            subKey = String.valueOf(params[0]) ;
        }  else if((params[0] instanceof User)){
            subKey = String.valueOf(((User)params[0]).getId());
        } else {
            throw new IllegalArgumentException("Not supported parameter type of KeyGenerator");
        }
        return cacheKey(subKey);
    }

    public String cacheKey(String subKey){
        return "user_" + subKey;
    }
}
