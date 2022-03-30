package com.example.demo.utils;


/**
 * @author i565244
 */
public class UidGeneratorTest2 {

    public static void main(String[] args) {

        UidGenerator uidGenerator = new UidGenerator();
        uidGenerator.setEpochStr("2022-03-01");
        uidGenerator.setTimeBits(61);
        uidGenerator.setSeqBits(1);
        uidGenerator.setWorkerBits(1);
        uidGenerator.init(1);
        while(true){
            var uid = uidGenerator.getUID();
            System.out.println("uid:" +  uid + ",\tts:" + uidGenerator.parseUID(uid));
        }

    }



}
