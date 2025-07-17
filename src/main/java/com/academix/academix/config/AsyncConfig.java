package com.academix.academix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Executor getAsyncExecutor() {
        if (threadPoolExecutor == null) {
            int minPoolSize = 5;        // initial thread size
            int maxPoolSize = 20;       // if queue is full, it will create till max of 10
            int queueSize = 30;         // total capacity of queue
            threadPoolExecutor = new ThreadPoolExecutor(
                    minPoolSize,
                    maxPoolSize,
                    1,
                    TimeUnit.HOURS,
                    new ArrayBlockingQueue<>(queueSize),
                    new EmailThreadFactory(),
                    new ThreadPoolExecutor.DiscardPolicy()
            );
        }

        return threadPoolExecutor;
    }
}


/*
AbortPolicy (default)	❌ Throws RejectedExecutionException immediately (default) (critical systems)
CallerRunsPolicy	✅ The caller thread runs the task (slows down caller, acts as backpressure) (small systems)
DiscardPolicy	🚮 Silently discards the task (batch task, email, logs -> same as DisacrdOldestPolicy
DiscardOldestPolicy	🔁 Discards the oldest task in the queue, then retries submitting


 */