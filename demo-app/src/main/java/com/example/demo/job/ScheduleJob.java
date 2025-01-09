package com.example.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author i565244
 */
@Slf4j
@Component
public class ScheduleJob {

    @Scheduled(cron = "${job.cron}")
    public void task(){
        log.info("ScheduleJob is running,hashCode is:{} ",this.hashCode());
    }
}
