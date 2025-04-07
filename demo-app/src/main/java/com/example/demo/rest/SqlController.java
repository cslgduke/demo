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


@RequestMapping(value = {"/sql"})
@Validated
@RestController
@Slf4j
public class SqlController {
    @Autowired
    private EntityManager entityManager;

    int concurrency = 5;
    int cnt = 100;

    @PostMapping("/queryFromTable")
    public Object queryFromTable() {
        var executor = new ThreadPoolExecutor(concurrency, concurrency, 10, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("hana-query-test").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                while (true) {
                    fromTable(concurrency);
//                    fromTable2(concurrency);
                }
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
                while (true) {
                    fromPartition(concurrency);
                }
            });
        }
        return "success";
    }

    public void fromTable(int concurrency) {
        var querySql = "select * from tl_user where age = 41";
//        var querySql = "SELECT date_code,avg(age) FROM tl_user WHERE age=35 GROUP BY date_code";

        long start = System.currentTimeMillis();
        var result = entityManager.createNativeQuery(querySql).getResultList();
        var cost = System.currentTimeMillis() - start;
        log.info("finish execute:[{}],concurrency:{} cost: {}ms",querySql,concurrency,cost);
    }

    public void fromTable2(int concurrency) {
        var querySql = "select * from tl_user_2 where age = 41";
//        var querySql = "SELECT date_code,avg(age) FROM tl_user WHERE age=35 GROUP BY date_code";

        long start = System.currentTimeMillis();
        var result = entityManager.createNativeQuery(querySql).getResultList();
        var cost = System.currentTimeMillis() - start;
        log.info("finish execute:[{}],concurrency:{} cost: {}ms",querySql,concurrency,cost);
    }

    public void fromPartition(int concurrency) {
        var querySql = "select * from tl_user_2 where age = 35";
//        var querySql = "SELECT date_code,avg(age) FROM tl_user WHERE age=35 GROUP BY date_code";

        long start = System.currentTimeMillis();
        var result = entityManager.createNativeQuery(querySql).getResultList();
        var cost = System.currentTimeMillis() - start;
        log.info("finish execute:[{}],concurrency:{} cost: {}ms",querySql,concurrency,cost);


    }
}