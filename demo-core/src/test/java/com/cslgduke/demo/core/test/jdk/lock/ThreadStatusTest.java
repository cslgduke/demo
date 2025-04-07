package com.cslgduke.demo.core.test.jdk.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author i565244
 */

@Slf4j
public class ThreadStatusTest {
    final static Object obj = new Object();

    public static void main(String[] args) {
        var t1 = new Thread(() ->{
               synchronized (obj) {
                   try {
                       obj.wait();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   log.info(">>>>>>>>>>>>>>");
               }
        });
        var t2 = new Thread(() ->{
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(">>>>>>>>>>>>>>");

            }
        });
        t1.start();
        t2.start();
        log.info("start.......");

        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("notify threads.......");
        synchronized (obj) {
            obj.notifyAll();
        }
    }

}
