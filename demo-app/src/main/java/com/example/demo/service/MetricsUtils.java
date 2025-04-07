package com.example.demo.service;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author i565244
 */
@Service
public class MetricsUtils {

    @Autowired
    private MeterRegistry registry;

    private HashMap<String, DistributionSummary> metrics = new HashMap<>();

    public DistributionSummary  getMetrics(String api,String tenant,String customer){
        String key = api+tenant+customer;
        metrics.putIfAbsent(key,registry.summary("rt_summary", "api", api,"tenant",tenant,"customer",customer));
        return metrics.get(key);
    }
}
