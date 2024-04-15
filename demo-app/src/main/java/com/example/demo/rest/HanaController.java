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


@RequestMapping(value = {"/hana"})
@Validated
@RestController
@Slf4j
public class HanaController {


    @Autowired
    private EntityManager entityManager;

    int concurrency = 1;

    @PostMapping("/queryFromCV")
    public Object queryFromCV() {
        var executor = new ThreadPoolExecutor(concurrency, concurrency, 10, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("hana-query-test").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                while(true){
                    queryFromCV(concurrency);
                }
            });
        }
        return "success";
    }

    @PostMapping("/queryFromCV2")
    public Object queryFromCV2() {
        var executor = new ThreadPoolExecutor(concurrency, concurrency, 10, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("hana-query-test").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                while(true){
                    queryFromCV2(concurrency);
                }
            });
        }
        return "success";
    }

    @PostMapping("/queryFromTable")
    public Object queryFromTable() {
        var executor = new ThreadPoolExecutor(concurrency, concurrency, 10, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("hana-query-test").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
//                while(true){
                    fromTable(concurrency);
//                }
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
            });
        }
        return "success";
    }

    public void queryFromCV(int concurrency){
        long start = System.currentTimeMillis();
        var querySql = "SELECT KPI_LIST_PRICE,\n" +
                "        KPI_PROMOTED_PRICE,\n" +
                "        DATE_CODE,\n" +
                "        PRODUCT_UUID\n" +
                "FROM \n" +
                "    (SELECT KPI_LIST_PRICE,\n" +
                "        KPI_PROMOTED_PRICE,\n" +
                "        DATE_CODE,\n" +
                "        PRODUCT_UUID\n" +
                "    FROM \n" +
                "        (SELECT DATE_CODE,\n" +
                "         PRODUCT_UUID,\n" +
                "         AVG(KPI_LIST_PRICE) AS KPI_LIST_PRICE,\n" +
                "         AVG(KPI_PROMOTED_PRICE) AS KPI_PROMOTED_PRICE\n" +
                "        FROM \"KPI_ALL_CV\"\n" +
                "        WHERE VERSION_UUID = '9b6888a3a4124b9480e2605c53b1dc4e'\n" +
                "        GROUP BY  DATE_CODE,PRODUCT_UUID ) )\n" +
                "    WHERE 1=1\n" +
                "ORDER BY  PRODUCT_UUID ASC,DATE_CODE ASC" ;
        var count = entityManager.createNativeQuery(querySql).getResultList();
        var cost = System.currentTimeMillis() - start;
        log.info("finish query data from KPI_ALL_CV,concurrency:{},cost:{}ms",concurrency,cost);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void queryFromCV2(int concurrency){
        long start = System.currentTimeMillis();
        var querySql = "SELECT KPI_LIST_PRICE,\n" +
                "        KPI_PROMOTED_PRICE,\n" +
                "        DATE_CODE,\n" +
                "        PRODUCT_UUID\n" +
                "FROM \n" +
                "    (SELECT KPI_LIST_PRICE,\n" +
                "        KPI_PROMOTED_PRICE,\n" +
                "        DATE_CODE,\n" +
                "        PRODUCT_UUID\n" +
                "    FROM \n" +
                "        (SELECT DATE_CODE,\n" +
                "         PRODUCT_UUID,\n" +
                "         AVG(KPI_LIST_PRICE) AS KPI_LIST_PRICE,\n" +
                "         AVG(KPI_PROMOTED_PRICE) AS KPI_PROMOTED_PRICE\n" +
                "        FROM \"KPI_ALL_CV2\"\n" +
                "        WHERE VERSION_UUID = '9b6888a3a4124b9480e2605c53b1dc4e'\n" +
                "        GROUP BY  DATE_CODE,PRODUCT_UUID ) )\n" +
                "    WHERE 1=1\n" +
                "ORDER BY  PRODUCT_UUID ASC,DATE_CODE ASC" ;
        var count = entityManager.createNativeQuery(querySql).getResultList();
        var cost = System.currentTimeMillis() - start;
        log.info("finish query data from KPI_ALL_CV2,concurrency:{},cost:{}ms",concurrency,cost);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fromTable(int concurrency){
        long start = System.currentTimeMillis();

//        var querySql = "select DATE_CODE ,PRODUCT_UUID,SUM(LIST_PRICE),SUM(BASELINE_VOLUME)  from DISAG_BASE_ITEM_TEST2 group by DATE_CODE ,PRODUCT_UUID";
//        var querySql = "select C_P_D,SUM(LIST_PRICE) from DISAG_BASE_ITEM_TEST2  group by C_P_D";
        var querySql = "select C_P_D,SUM(LIST_PRICE) from DISAG_BASE_ITEM_TEST2  group by C_P_D LIMIT 20";
//        var querySql = "select C_P_D,SUM(LIST_PRICE),SUM(BASELINE_VOLUME) from DISAG_BASE_ITEM_TEST2  group by C_P_D";


//        var querySql = "select DATE_CODE ,PRODUCT_UUID,SUM(LIST_PRICE),SUM(BASELINE_VOLUME)  from DISAG_BASE_ITEM  WHERE ACCOUNT_PLAN_UUID = '0e402f4d311a4891ad8a902f7d5ac3f5'  group by DATE_CODE ,PRODUCT_UUID";
//        var querySql = "select ACCOUNT_PLAN_UUID,SUM(LIST_PRICE)  from DISAG_BASE_ITEM  group by ACCOUNT_PLAN_UUID";


//        var querySql = "select  PRODUCT_UUID,SUM(LIST_PRICE) from DISAG_BASE_ITEM group by PRODUCT_UUID";
//        var querySql = "select DATE_CODE ,PRODUCT_UUID,SUM(LIST_PRICE),SUM(BASELINE_VOLUME) from DISAG_BASE_ITEM_TEST2 group by DATE_CODE ,PRODUCT_UUID";
        var count = entityManager.createNativeQuery(querySql).getResultList();
        var cost = System.currentTimeMillis() - start;
        log.info("finish execute:[{}],concurrency:{} cost: {}ms",querySql,concurrency,cost);
    }


    public void fromPartition(int concurrency){
        long start = System.currentTimeMillis();
//        var querySql = "select sum(LIST_PRICE), ACCOUNT_PLAN_UUID from DISAG_BASE_ITEM_TEST  WHERE ACCOUNT_PLAN_UUID  = '021548ce0a9c4cb1b92d9ac35033fc26'  group by ACCOUNT_PLAN_UUID";
        var querySql = "select ACCOUNT_PLAN_UUID,SUM(LIST_PRICE)  from DISAG_BASE_ITEM_TEST  WHERE DISAG_VERSION_UUID = 'DEFAULT_VERSION'  group by ACCOUNT_PLAN_UUID";
        var count = entityManager.createNativeQuery(querySql).getResultList();
        var cost = System.currentTimeMillis() - start;
        log.info("finish execute:[{}],concurrency:{} cost :{}ms",querySql,concurrency,cost);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}