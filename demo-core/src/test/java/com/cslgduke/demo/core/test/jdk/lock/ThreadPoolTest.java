package com.cslgduke.demo.core.test.jdk.lock;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author i565244
 */

@Slf4j
public class ThreadPoolTest {

    public static void main(String[] args) {
        var executor = new ThreadPoolExecutor(10, 20, 10, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("mytest").build(),
                new ThreadPoolExecutor.AbortPolicy());
//        for (int i = 0; i < 1; i++) {
//            executor.execute(() ->{
//                log.info("Hello world");
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                executor.execute(()-> log.info("new Task"));
//            });
//        }
        for (int i = 0; i < 5; i++) {
            executor.execute(() -> log.info("Hello World"));
        }

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("executor info:{}",executor);
//        System.exit(0);
    }
}
