package com.academix.academix.config;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class EmailThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        AtomicInteger threadNumber = new AtomicInteger(1);
        Thread t = new Thread(r);
        t.setName("EmailThread-" + threadNumber.getAndIncrement());
        return t;
    }
}
