package com.cslgduke.demo.core.test.jdk.uuid;

/**
 * @author i565244
 */
public class UUIDGenerator {

    private long sequence = 0L;
    private int sequenceBits = 10;

    public synchronized long nextSequence() {
        sequence = (sequence + 1) & ((1 << sequenceBits) - 1);
        return sequence;
    }

    public static void main(String[] args) throws InterruptedException {
        UUIDGenerator ug = new UUIDGenerator();
//        for (int i = 0; i < 1100; i++) {
//            System.out.println(ug.nextSequence());
//        }

        for (int i = 0; i < 100; i++) {
            int processIdBits = 10;
            int sequenceBits = 2;

            long beginTs = 1483200000000L;
            long ts = System.currentTimeMillis();
            var a =  ((ts - beginTs) << (processIdBits + sequenceBits)) | (1024 << sequenceBits) | ug.nextSequence();
            System.out.println("ts:\t" + ts + ",id:" + a);
            Thread.sleep(50L);
        }

    }
}
