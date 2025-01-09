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
        while (true) {
            testTask();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


//        log.info("executor info:{}",executor);
//        System.exit(0);
    }

    public static void testTask() {
        var executor = new ThreadPoolExecutor(10, 20, 10, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(100), new ThreadFactoryBuilder().setNamePrefix("mytest").build(), new ThreadPoolExecutor.AbortPolicy());
//        executor.allowCoreThreadTimeOut(true);
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> log.info("Hello World"));
        }
    }
}
