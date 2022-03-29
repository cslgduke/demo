package com.example.demo.utils;


import cn.amorou.uid.worker.SimpleWorkerIdAssigner;

import java.util.concurrent.TimeUnit;

/**
 * @author i565244
 */
public class UidGeneratorTest {

    public static void main(String[] args) {

        UidGenerator uidGenerator = new UidGenerator();
        uidGenerator.setEpochStr("2022-03-29");
        uidGenerator.setTimeBits(61);
        uidGenerator.setSeqBits(1);
        uidGenerator.setWorkerBits(1);
        uidGenerator.init(0);
        while(true){
            var uid = uidGenerator.getUID();
            System.out.println("uid:" +  uid + ",\tts:" + uidGenerator.parseUID(uid));
        }

    }



}
