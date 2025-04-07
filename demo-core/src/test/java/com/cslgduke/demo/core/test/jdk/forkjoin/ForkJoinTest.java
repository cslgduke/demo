package com.cslgduke.demo.core.test.jdk.forkjoin;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author i565244
 */
@Slf4j
public class ForkJoinTest {

    @Test
    public void testNoResultTask() throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(new PrintTask(1, 50));
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();

    }

    class PrintTask extends RecursiveAction {

        private static final long serialVersionUID = 1L;
        private static final int THRESHOLD = 9;

        private int start;
        private int end;

        public PrintTask(int start, int end) {
            super();
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start < THRESHOLD) {
                for (int i = start; i <= end; i++) {
                    log.info("i=" + i);
                }
            } else {
                int middle = (start + end) / 2;
                PrintTask firstTask = new PrintTask(start, middle);
                PrintTask secondTask = new PrintTask(middle + 1, end);
                invokeAll(firstTask, secondTask);
            }
        }
    }


    @Test
    public void testHasResultTask() throws InterruptedException, ExecutionException {
        int result1 = 0;
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            result1 += i;
        }
        log.info("loop calculate 1-1000000 accumulated value：{} ,cost:{}", result1, System.currentTimeMillis() - start);

        ForkJoinPool pool = new ForkJoinPool();
        start = System.currentTimeMillis();
        ForkJoinTask<Integer> task = pool.submit(new CalculateTask(1, 1000000));

        int result2 = task.get();
        log.info("concurently calcualte 1-1000000 accumulated value：{},cost:{}", result2, System.currentTimeMillis() - start);
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();
    }

    public class CalculateTask extends RecursiveTask<Integer> {

        private static final long serialVersionUID = 1L;
        private static final int THRESHOLD = 49;
        private int start;
        private int end;

        public CalculateTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= THRESHOLD) {
                int result = 0;
                for (int i = start; i <= end; i++) {
                    result += i;
                }
                return result;
            } else {
                int middle = (start + end) / 2;
                CalculateTask firstTask = new CalculateTask(start, middle);
                CalculateTask secondTask = new CalculateTask(middle + 1, end);
                invokeAll(firstTask, secondTask);
                return firstTask.join() + secondTask.join();
            }
        }
    }

    @Test
    public void testHttpTask() throws InterruptedException, ExecutionException {
        ForkJoinPool pool = new ForkJoinPool();
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            urls.add("http://test-" + i);
        }
        var task = pool.submit(new HttpTask(urls));
        var result = task.get();
        log.info("forkjoin http invoke result:{}", JSON.toJSONString(result));
        pool.awaitTermination(10, TimeUnit.SECONDS);
        pool.shutdown();

    }

    class HttpTask extends RecursiveTask<HashMap<String, String>> {

        private static final long serialVersionUID = 1L;


        private List<String> urls;


        public HttpTask(List<String> urls) {
            super();
            this.urls = urls;
        }

        @Override
        protected HashMap compute() {
            if (urls.size() == 1) {
                var result = RandomUtil.randomString(10);
                log.info("invoke url:{},result:{}", urls.get(0), result);
                var map = new HashMap<String, String>();
                map.put(urls.get(0), result);
                return map;
            } else {
                List<HttpTask> forkTasks = new ArrayList<>();
                urls.forEach(url -> forkTasks.add(new HttpTask(Arrays.asList(url))));
                invokeAll(forkTasks);
                var map = new HashMap<String, String>();
                forkTasks.forEach(task -> map.putAll(task.join()));
                return map;
            }
        }
    }


    @Test
    public void testForkJoinPool() throws ExecutionException, InterruptedException {
//        ExecutorService es = Executors.newWorkStealingPool();
//        for (int i = 0; i < 10; i++) {
//            es.execute(() -> log.info("task is running.."));
//        }

        var list = new ArrayList<DemoTask>();
        for (int i = 0; i < 20; i++) {
            var task = new DemoTask<Integer>();
            task.setUrl("http://test-service/" + RandomUtil.randomString(5));
            list.add(task);
        }


        ForkJoinPool forkJoinPool = new ForkJoinPool();
        var resultList = new ArrayList<DemoTask>();
        forkJoinPool.submit(() -> {
            var tasks = list.parallelStream().map(task -> {
                log.info("invoke url:{}",task.getUrl());
                var result = RandomUtil.randomInt(10);
                task.setResult(result);
                return task;
            }).collect(Collectors.toList());
            resultList.addAll(tasks);
        });
        log.info("task result:{}",JSON.toJSONString(resultList));
    }

    public class DemoTask<T>{

        String url;
        T result;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public T getResult() {
            return result;
        }

        public void setResult(T result) {
            this.result = result;
        }
    }




}

