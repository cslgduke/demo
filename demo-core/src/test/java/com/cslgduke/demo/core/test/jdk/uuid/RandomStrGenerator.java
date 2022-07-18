package com.cslgduke.demo.core.test.jdk.uuid;

import cn.hutool.core.util.RandomUtil;

import java.util.HashSet;

/**
 * @author i565244
 */
public class RandomStrGenerator {

    public static void main(String[] args) {
        var sets = new HashSet<String>();
        var dupCount = 0;
        for (int i = 0; i < 10; i++) {
            var tmp = RandomUtil.randomString(8);
            if(!sets.contains(tmp)){
                sets.add(tmp);
                System.out.println("random number:" + tmp);
            }else{
                dupCount++;
                System.out.println("duplicate random number:" + tmp);
            }
        }
        System.out.println("dupCount : " + dupCount);
    }
}
