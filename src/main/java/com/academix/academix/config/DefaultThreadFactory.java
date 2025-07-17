package com.academix.academix.config;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        AtomicInteger threadNumber = new AtomicInteger(1);
        Thread t = new Thread(r);
        t.setName("DefaultThread-" + threadNumber.getAndIncrement());
        return t;
    }
}
