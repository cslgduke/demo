package com.cslgduke.demo.core.test.jdk.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author i565244
 */
@Slf4j
public class LockSupportTest {

    public static void main(String[] args) {
        Thread t = new Thread( () ->{
            while(true){
                log.info("ready to execute");
                LockSupport.park();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("finsh execute");
            }
        });
        t.start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(t);
    }
}
