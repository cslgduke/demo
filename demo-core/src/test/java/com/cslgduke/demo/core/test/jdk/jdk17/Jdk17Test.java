package com.cslgduke.demo.core.test.jdk.jdk17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author i565244
 */
@Slf4j
public class Jdk17Test {

    @Test
    public void test_textBlock() {
        String poem = """
                Twinkle, Twinkle, Little Star

                Twinkle, twinkle, little star,
                How I wonder what you are!
                Up above the world so high,
                Like a diamond in the sky.

                Twinkle, twinkle, little star,
                How I wonder what you are!
            """;
        log.info("text block poem \n:{}",poem);
    }

}
