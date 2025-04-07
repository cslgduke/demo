package com.cslgduke.demo.core.test.jdk;

import org.junit.jupiter.api.Test;

import java.util.UUID;

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


        for (int i = 0; i < 100; i++) {
            String uuid = UUID.randomUUID().toString();
            Integer uuidNumber = uuid.replaceAll("-","").hashCode();
            System.out.println("uuid:" + uuid + ",hashcode:" + uuidNumber);
        }

        System.out.println("max long:" + Long.MAX_VALUE);

        long a = 9223372036854775807l;

        System.out.println("current millis:" + System.currentTimeMillis());
        System.out.println(Long.toBinaryString(-1L));
        System.out.println(Long.toBinaryString(-1L << 4));
        System.out.println(Long.toBinaryString(-1L << 6));


        System.out.println(Long.toBinaryString(~(-1L << 4)));

        System.out.println(Long.toBinaryString(~(-1L << 6)));
    }


}
