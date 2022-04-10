package com.cslgduke.demo.core.test.jdk;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

/**
 * @author i565244
 */
public class FunctionTest {

    @Test
    public void testFunction(){
       Function<Integer,String> fa = t -> {
           return RandomUtil.randomString(5)+ "-" + t;
       };
       System.out.println(fa.apply(8));

    }
}
