package com.example.demo.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * @author i565244
 */
@Component
@Slf4j
public class AccountService {
    @Autowired
    private MeterRegistry registry;

    //deposit_counter_total
    private Counter depositCounter;

    private Counter withdrawCounter;

    private DistributionSummary depositAmountSummary;

    private DistributionSummary withdrawAmountSummary;

    private BigDecimal balance = new BigDecimal(1000);

    @PostConstruct
    private void init() {
        //deposit_counter_total (event occur count)
        depositCounter = registry.counter("deposit_counter", "currency", "btc");
        //withdraw_counter_total
        withdrawCounter = registry.counter("withdraw_counter", "currency", "btc");
        //deposit_amount_count  deposit_amount_max deposit_amount_sum
        depositAmountSummary = registry.summary("deposit_amount", "currency", "btc");
        withdrawAmountSummary = registry.summary("withdraw_amount", "currency", "btc");

        //balanceGauge
        Gauge.builder("balanceGauge", () -> balance)
                .tags("currency", "btc")
                .description("余额")
                .register(registry);
    }


    public void depositOrder(BigDecimal amount) {
        log.info("depositOrder amount:{}", amount);
        try {
            //余额增加
            balance = balance.add(amount);
            //充值笔数埋点
            depositCounter.increment();
            //充值金额埋点
            depositAmountSummary.record(amount.doubleValue());

        } catch (Exception e) {
            log.info("depositOrder error", e);
        } finally {
            log.info("depositOrder result:{}", amount);
        }
    }

    public void withdrawOrder(BigDecimal amount) {
        log.info(" withdrawOrder amount:{}", amount);
        try {
            if (balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("balance insufficient,withdraw fail");
            }
            //余额减少
            balance = balance.subtract(amount);
            // 提现笔数埋点数据
            withdrawCounter.increment();
            // 提现金额埋点
            withdrawAmountSummary.record(amount.doubleValue());
        } catch (Exception e) {
            log.info("withdrawOrder error", e);
        } finally {
            log.info("withdrawOrder result:{}", amount);
        }
    }
}
