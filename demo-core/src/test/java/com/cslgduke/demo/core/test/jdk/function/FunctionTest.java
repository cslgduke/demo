package com.cslgduke.demo.core.test.jdk.function;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Consumer;
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

    @Test
    public void testConsmuer(){
        var strList = Arrays.asList("a","b","c");
        strList.forEach(t ->{
            System.out.println("hello-" + t);
        });

        Consumer<String> consumerA = t ->{
            t = t + "_consumer";
            System.out.println(t);
        };
        strList.forEach(consumerA);
    }
}
