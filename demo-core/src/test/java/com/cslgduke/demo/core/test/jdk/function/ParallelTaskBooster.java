package com.cslgduke.demo.core.test.jdk.function;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author i565244
 */
public class ParallelTaskBooster {

    public <K,R> List<R> genericParallel(List<K> inputList, Function<K,R> fn, Executor threadPoolExecutor){
        List<CompletableFuture<R>> futures = inputList.stream().map(v -> CompletableFuture.supplyAsync(() -> fn.apply(v), threadPoolExecutor))
                        .collect(Collectors.toList());
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
//        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public <K,R> R convert(K k,Function<K,R> fn){
        return fn.apply(k);
    }
}
