package com.cslgduke.demo.core.test.encryption;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author i565244
 */
@Slf4j
public class EncryptionTest {

    @Test
    public void test_des() throws NoSuchAlgorithmException {
        for (int i = 0; i < 10; i++) {
            log.info("random str:{}",RandomUtil.randomString(32));
        }

    }

    @Test
    public void test_base64()  {
        Base64.Encoder baseEncoder = Base64.getEncoder();
        var originStr = "abc";
        var encryptStr =  Base64Utils.encodeToString(originStr.getBytes());
        var decryptStr = String.valueOf(Base64Utils.decodeFromString(encryptStr));

        log.info("originStr:{},encryptStr:{},decryptStr:{},test success:{}",originStr,encryptStr,decryptStr,originStr.equals(decryptStr));

    }



}
