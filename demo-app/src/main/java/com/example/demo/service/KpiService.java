package com.example.demo.service;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author i565244
 */
@Component
@Slf4j
public class KpiService {
    @Autowired
    private MetricsUtils metricsUtils;

    public void kpiCalculate(String tenant,String customer) throws InterruptedException {
        int cost = RandomUtil.randomInt(300,500);
        Thread.sleep(cost);
        metricsUtils.getMetrics("kpiCalculate",tenant,customer).record(cost);
    }


}
