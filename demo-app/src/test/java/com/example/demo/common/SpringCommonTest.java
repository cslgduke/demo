package com.example.demo.common;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.example.demo.bo.User;
import com.example.demo.config.ValidatorManager;
import com.example.demo.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author i565244
 */
@SpringBootTest
@Slf4j
public class SpringCommonTest {
    @Autowired
    private ValidatorManager validatorManager;


    @Test
    public void test_listBean() {

        log.info("ValidatorManager validatorList:{}",validatorManager.getValidatorList());
    }
}
