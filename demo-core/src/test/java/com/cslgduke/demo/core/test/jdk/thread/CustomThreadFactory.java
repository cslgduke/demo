package com.cslgduke.demo.core.test.jdk.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author i565244
 */
public class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger counter = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            String name = "cslgduke-test-" + counter.getAndIncrement();
            return new Thread(r, name);
        }
}
