package com.cslgduke.demo.core.test.jdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author i565244
 */
@Slf4j
public class CommonTest {

    @Test
    public void test_strFormat() {
        var str =
        String.format("%s %s %s", "Fist", null , null);
        log.info("str format result:{}",str);
    }
}
