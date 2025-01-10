package com.example.demo.job;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.example.demo.service.Userservice;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author i565244
 */
@Slf4j
@Component
@DisallowConcurrentExecution
public class DemoQuartzJob implements org.quartz.Job{

    @Autowired
    private Userservice userservice;

    @Autowired
    private RedissonClient redissonClient;

    private static final int CORE_POOL_SIZE = 10;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE, 10, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(100), new ThreadFactoryBuilder().setNamePrefix("jobThread-").build(), new ThreadPoolExecutor.AbortPolicy());

    static List<Integer> lists = new ArrayList<>();

    static{
        for (int i = 0; i < CORE_POOL_SIZE; i++) {
            lists.add(i);
        }
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        var lock = redissonClient.getLock("DemoQuartzJob");
        var tryLock = false;
        try {
            log.info("ready to run DemoQuartzJob,hasCode:{}", this.hashCode());
            tryLock = lock.tryLock();
            if(!tryLock){
                return;
            }
            log.info("get lock successful, prepare to start DemoQuartzJob");
            lists.parallelStream().map(String::valueOf)
                    .map(t -> CompletableFuture.supplyAsync(() -> task(), executor))
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());

//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }  catch (Throwable throwable) {
            log.error("DemoQuartzJob encounter exception: ", throwable);
        } finally {
            if(tryLock){
                lock.unlock();
                log.info("release lock DemoQuartzJob");
            }
        }
    }


    private  boolean task(){
        log.info("task run");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
