package com.cslgduke.demo.core.test.jdk.thread;


import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author i565244
 */
@Slf4j
public class ThreadTest {

    @Test
    public void test_parallel_stream(){
        List<String> list = Arrays.asList("Sto","Zto","YTO","EMS");
        var prices = list.parallelStream().map(this::priceCalculate).collect(Collectors.toList());
        log.info("prices:{}",prices);
    }

    private String priceCalculate(String shop){
        log.info("priceCalculate,param:{}",shop);
        return "shop:" + shop + ",price:"+ RandomUtil.randomDouble();
    }

    @Test
    public void test_completable_future() throws ExecutionException, InterruptedException {
        var executor = Executors.newFixedThreadPool(100);
        CompletableFuture<String>  future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //is daemon true
            log.info("CompletableFuture async isDaemon:{}",Thread.currentThread().isDaemon());
//            int a = 1/0;
            return "result";
        },executor);
        // use main thread
        // use specific executor by supplyAsync second param
        future.whenComplete((s,e) -> {
            //s代表正常执行结果，e代表执行异常
            log.info("whenComplete async execute result:{}",s);
            log.error("whenComplete async execute error ",e);
        });

        //user sub thread,default:ForkJoinPool
        future.whenCompleteAsync((s,e) -> {
            log.info("whenCompleteAsync async execute result:{}",s);
            log.error("whenCompleteAsync async execute error ",e);
        });

        //user specific sub thread
        future.whenCompleteAsync((s,e) -> {
            log.info("whenCompleteAsync async execute,use specific thread,result:{}",s);
            log.error("whenCompleteAsync async execute,use specific thread, error ",e);
        },executor);
        //如果任务没有执行就返回默认值
        log.info("result:{}",future.getNow("now"));
        log.info("result:{}",future.get());
    }

    @Test
    public void test_threadloacal(){
        ContextManager.setTenantId("T_10086");
        ContextManager.setUserId("ZhangSan");
        //最佳线程数=CPU核数×[1+(I/O耗时/CPU耗时)]
        //线程数=CPU核数×目标CPU利用率×(1+平均等待时间/平均工作时间)
//        var executor = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors());

        var executor = Executors.newFixedThreadPool( 2);
        for (int i = 1; i < 10; i++) {
            ContextManager.setTenantId("T_10086" + i);
            executor.execute(this::asyncFunction);
        }
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
        }

        for (int i = 1; i < 10; i++) {
            ContextManager.setTenantId("T_10086" + i);
            executor.execute(new ContextRunnable(this::asyncFunction,ContextManager.getTenantId()));
        }
        while(true){}
    }

    public class ContextRunnable implements  Runnable{

        private  Runnable runnable;

        private  String tenantId;

        private ContextRunnable(Runnable runnable, String tenantId) {
            this.runnable = runnable;
            this.tenantId = new String(tenantId);
        }
        @Override
        public void run() {
            beforeRun();
            runnable.run();
            afterRun();
        }

        private void beforeRun(){
            ContextManager.setTenantId(tenantId);
        }

        private  void afterRun(){
            ContextManager.reset();
        }
    }

    public void asyncFunction(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        log.info("ThreadLocal variable tenantId:{},userId:{}",ContextManager.getTenantId(),ContextManager.getUserId());
    }



    @Test
    public void test_threadloacal_ex() throws InterruptedException {
        var executor = new ThreadPoolExecutor(100, 100, 10, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("mytest").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 1; i++) {
            executor.execute(() ->{
                log.info("Hello world");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executor.execute(()-> log.info("new Task"));
            });
        }
        log.info("executor info:{}",executor);
    }


    public static void main(String[] args) {
        var executor = new ThreadPoolExecutor(100, 100, 10, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("mytest").build(),
                new ThreadPoolExecutor.AbortPolicy());
//        for (int i = 0; i < 1; i++) {
//            executor.execute(() ->{
//                log.info("Hello world");
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                executor.execute(()-> log.info("new Task"));
//            });
//        }
        for (int i = 0; i < 5; i++) {
            executor.execute(() -> log.info("Hello World"));
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("executor info:{}",executor);
        System.exit(0);
    }
}
