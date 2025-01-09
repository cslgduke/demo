package com.example.demo.job;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.example.demo.service.Userservice;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author i565244
 */
@Slf4j
@Component
public class DemoQuartzJob implements org.quartz.Job{

    @Autowired
    private Userservice userservice;

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(100), new ThreadFactoryBuilder().setNamePrefix("jobThread-").build(), new ThreadPoolExecutor.AbortPolicy());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> log.info("Hello World"));
        }
        log.info("DemoQuartzJob is running,randomString is:{},hashCode:{}",userservice.randomString(10),this.hashCode());
    }
}
