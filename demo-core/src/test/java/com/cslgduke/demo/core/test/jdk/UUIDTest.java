package com.cslgduke.demo.core.test.jdk;

import org.junit.jupiter.api.Test;

/**
 * @author i565244
 */
public class UUIDTest {

    @Test
    public void testApi(){
//        for (int i = 0; i < 10; i++) {
//            System.out.println("currentTimeMillis: " + System.currentTimeMillis());
//        }
//
//        for (int i = 0; i < 10; i++) {
//            System.out.println("nanoTime: " + System.nanoTime());
//        }

        System.out.println(Long.toBinaryString(-1L));

        System.out.println(Long.toBinaryString(-1L << 4));

        System.out.println(Long.toBinaryString(-1L << 6));


        System.out.println(Long.toBinaryString(~(-1L << 4)));

        System.out.println(Long.toBinaryString(~(-1L << 6)));
    }


}
