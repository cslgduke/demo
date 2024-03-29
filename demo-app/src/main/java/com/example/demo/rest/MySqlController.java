package com.example.demo.rest;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@RequestMapping(value = {"/mysql"})
@Validated
@RestController
@Slf4j
public class MySqlController {
    @Autowired
    private EntityManager entityManager;

    int concurrency = 1;
    int cnt = 100;

    @PostMapping("/queryFromTable")
    public Object queryFromTable() {
        var executor = new ThreadPoolExecutor(concurrency, concurrency, 10, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("hana-query-test").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                while(true){
                    fromTable(concurrency);
                }
//                long start = System.currentTimeMillis();
//                for (int j = 0; j < cnt; j++) {
//                    fromTable(concurrency);
//                }
//                log.info("finish queryFromTable {},cost:{}ms",cnt,System.currentTimeMillis()-start);
            });
        }
        return "success";
    }

    @PostMapping("/queryFromPartition")
    public Object queryFromPartition() {
        var executor = new ThreadPoolExecutor(concurrency, concurrency, 10, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("hana-query-test").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                while(true){
                    fromPartition(concurrency);
                }
//                long start = System.currentTimeMillis();
//                for (int j = 0; j < cnt; j++) {
//                    fromPartition(concurrency);
//                }
//                log.info("finish queryFromPartition {},cost:{}ms",cnt,System.currentTimeMillis()-start);
            });
        }
        return "success";
    }

    public void fromTable(int concurrency){
//        long sumCost = 0;
//        int cnt = 0;
            long start = System.currentTimeMillis();
            var querySql = "select * from tl_user where age = 35";
            var count = entityManager.createNativeQuery(querySql).getResultList();
            var cost = System.currentTimeMillis() - start;
            log.info("finish query data from tl_user,concurrency:{} cost :{}ms",concurrency,cost);
//            sumCost += cost;
//            if( ++cnt  ==  10){
//                log.info("latest {} queries data from tl_user,concurrency:{} cost :{}ms",cnt,concurrency,sumCost);
//                sumCost = 0;
//                cnt = 0;
//            }
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

    }

    public void fromPartition(int concurrency){
//        long sumCost = 0;
//        int cnt = 0;
            long start = System.currentTimeMillis();
            var querySql = "select * from tl_user_2 where age = 35;";
            var count = entityManager.createNativeQuery(querySql).getResultList();
            var cost = System.currentTimeMillis() - start;
            log.info("finish query data from tl_user_partition,concurrency:{} cost :{}ms",concurrency,cost);
//            sumCost += cost;
//            if(++cnt  ==  10){
//                log.info("latest {} queries data from tl_user_partition,concurrency:{} cost :{}ms",cnt,concurrency,sumCost);
//                sumCost = 0;
//                cnt = 0;
//            }
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


    }
}