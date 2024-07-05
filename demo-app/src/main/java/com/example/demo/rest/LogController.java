package com.example.demo.rest;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.RandomUtil;
import com.example.demo.service.DisagService;
import com.example.demo.service.KpiService;
import com.example.demo.service.MetricsUtils;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@RequestMapping(value = {"/log"})
@Validated
@RestController
@Slf4j
@Data
public class LogController {

    @Autowired
    private MeterRegistry registry;

    @Autowired
    private DisagService disagService;

    @Autowired
    private KpiService kpiService;

    @Autowired
    private MetricsUtils metricsUtils;

    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    int concurrency = 20;

    List<String> logKeys = Arrays.asList("CreatePromotion", "CreateAccountPlan", "PromotionList", "AccountPlanDashBoard");
    List<String> resultKeys = Arrays.asList("Y", "F");

    private DistributionSummary createSummary;
    private DistributionSummary updateSummary;


    private List<String> tenants = Arrays.asList("tenant-01", "tenant-02");
    private List<String> customs = Arrays.asList("Walmart", "Cosco", "Aldi");

    @GetMapping("/createPromotion")
    public Object createPromotion() throws InterruptedException {
        long start = System.currentTimeMillis();
        String tenant = tenants.get(RandomUtil.randomInt(5) == 0 ? 0 : 1);
        String custom = customs.get(RandomUtil.randomInt(3));

        disagService.disaggregation(tenant, custom);
        kpiService.kpiCalculate(tenant, custom);

        metricsUtils.getMetrics("createPromotion",tenant,custom).record(System.currentTimeMillis() - start);
        return "success";
    }


    @GetMapping("/promotionList")
    public Object promotionList() throws InterruptedException {
        long start = System.currentTimeMillis();
        String tenant = tenants.get(RandomUtil.randomInt(5) == 0 ? 0 : 1);
        String custom = customs.get(RandomUtil.randomInt(3));

        DistributionSummary createSummary = registry.summary("rt_summary", "api", "promotionList", "tenant", tenant, "customer", custom);

        Thread.sleep(RandomUtil.randomInt(200, 1000));
        metricsUtils.getMetrics("promotionList",tenant,custom).record(System.currentTimeMillis() - start);
        return "success";
    }

    @PostMapping("/test")
    public Object test() {
        var executor = new ThreadPoolExecutor(concurrency, concurrency, 10, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("hana-query-test").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                while (true) {
                    try {
                        testLog();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return "success";
    }

    public void testLog() throws IOException {
        var logMsg = "time=" + LocalDateTime.now().format(dtf) + " logKey=" + logKeys.get(RandomUtil.randomInt(logKeys.size())) + " result=" + resultKeys.get(RandomUtil.randomInt(2)) + " cost=" + RandomUtil.randomInt(20, 50);
        log.info(logMsg);
        File outputFile = new File("/Users/i565244/logs/test.log");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile, true)));
        bw.write(logMsg + "\n");
        bw.close();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}