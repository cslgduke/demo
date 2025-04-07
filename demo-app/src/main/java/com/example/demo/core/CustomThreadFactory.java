package com.example.demo.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author i565244
 */
public class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger counter = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            String name = "cslgduke-demo-" + counter.getAndIncrement();
            return new Thread(r, name);
        }
}
