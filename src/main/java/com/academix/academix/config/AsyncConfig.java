package com.academix.academix.config;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {

    private ThreadPoolExecutor threadPoolExecutor;

    private final AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler;

    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(30);
        executor.setThreadNamePrefix("emailExecutor-");         // thread factory
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }

    @Bean(name = "logExecutor")
    public Executor logExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(30);
        executor.setThreadNamePrefix("logExecutor-");         // thread factory
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    // you can create more executor based on its domain, use @Async("beanName) for using domain-specific executor

    // Default executor
    @Override
    public Executor getAsyncExecutor() {        // whatever executor this one return, it is the one spring will use as default
        if (threadPoolExecutor == null) {       // ensure that the bean is singleton
            int minPoolSize = 5;        // initial thread size
            int maxPoolSize = 20;       // if queue is full, it will create till max of 10
            int queueSize = 30;         // total capacity of queue
            threadPoolExecutor = new ThreadPoolExecutor(
                    minPoolSize,
                    maxPoolSize,
                    1,
                    TimeUnit.HOURS,
                    new ArrayBlockingQueue<>(queueSize),
                    new DefaultThreadFactory(), // Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy()
            );
        }

        return threadPoolExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return this.asyncUncaughtExceptionHandler;
    }
}


/*
AbortPolicy (default)	❌ Throws RejectedExecutionException immediately (default) (critical systems)
CallerRunsPolicy	✅ The caller thread runs the task (slows down caller, acts as backpressure) (small systems)
DiscardPolicy	🚮 Silently discards the task (batch task, email, logs -> same as DisacrdOldestPolicy
DiscardOldestPolicy	🔁 Discards the oldest task in the queue, then retries submitting
 */