package com.example.demo.service;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author i565244
 */
@Service
public class DisagService {
    @Autowired
    private MetricsUtils metricsUtils;

    public void disaggregation(String tenant,String customer) throws InterruptedException {
        int cost = RandomUtil.randomInt(100,200);
        Thread.sleep(cost);
        metricsUtils.getMetrics("disaggregation",tenant,customer).record(cost);
    }

}
