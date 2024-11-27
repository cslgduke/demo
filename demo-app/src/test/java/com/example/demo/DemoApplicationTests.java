package com.example.demo;

import cn.amorou.uid.UidGenerator;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class DemoApplicationTests {

    @Resource
    private UidGenerator uidGenerator;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test_uid() {
        for (int i = 0; i < 100; i++) {
            System.out.println(uidGenerator.getUID());
        }
    }


    @Test
    public void test_distributionLock() {
        var executor = new ThreadPoolExecutor(10, 20, 10, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("mytest").build(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                redissonClient.getSpinLock("test_lock").lock();
                log.info("acquired lock, begin to run");
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("task finish, lease lock");
                redissonClient.getSpinLock("test_lock").unlock();
            });
        }

        while (true){
            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("heartbeat..........");
        }
    }
}
