package com.cslgduke.demo.core.test.jdk.gc;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.RandomUtil;
import com.cslgduke.demo.core.test.jdk.thread.CustomThreadFactory;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author
 */
public class GcTest {

    public static void main(String[] args) {
//        testYgc();
        testFgc();
    }

    public static void  testYgc(){
        var executor = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            executor.execute(() ->{
                while (true){
                    getInstance();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public static void  testFgc(){
        int corePoolSize = 20;
//        var executor = Executors.newFixedThreadPool(corePoolSize);
        var executor = new ThreadPoolExecutor(corePoolSize,
                corePoolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new CustomThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < corePoolSize; i++) {
            var persons = new ArrayList<Person>();
            executor.execute(() ->{
                while (true){
                    persons.add(getInstance());
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    
    
    @Data
    @Builder
    public static class Person{
        private String idNo;
        private String name;
        private int age;
        private double creditScore;
    }
    public static Person getInstance(){
        return Person.builder()
                .idNo(RandomUtil.randomNumbers(18))
                .name(RandomUtil.randomString(12))
                .age(RandomUtil.randomInt(0,100))
                .creditScore(RandomUtil.randomDouble(0,80)).build();
    }
}
