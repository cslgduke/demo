package com.cslgduke.demo.core.test.jdk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author i565244
 */
@Slf4j
public class StreamTest {

    @Test
    public void test_group() {
        var u1 = new User("1001","zhangsan",25,"123");
        var u2 = new User("1002","lisi",25,"123");
        var u3 = new User("1003","wangwu",30,"123");

        var u4 = new User("2001","张三",25,"123");
        var u5 = new User("2002","李四",25,"123");
        var u6 = new User("2003","王五",25,"123");

        List<User> users = Arrays.asList(u1,u2,u3,u4,u5,u6);
        var map = users.stream().collect(Collectors.groupingBy(u -> u.getId().substring(0,3)));
        map.forEach((k,v) -> {
            log.info("key:{},value:{}",k,v);
        });

        users.forEach(u -> u.setAge(100));
        System.out.println(users);

    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class User{
        private String id;
        private String name;
        private int age;
        private String password;
    }



}
