package com.cslgduke.demo.core.test.jdk.pair;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

/**
 * @author i565244
 */
@Slf4j
public class PairTest {

    @Test
    public void test_Pair() {
        var a = Pair.of(1,"100");
    }
}
