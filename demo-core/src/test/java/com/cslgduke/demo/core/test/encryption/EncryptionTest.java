package com.cslgduke.demo.core.test.encryption;

import cn.hutool.core.util.RandomUtil;
import com.cslgduke.demo.core.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

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
}
