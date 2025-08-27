package com.academix.academix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    @Bean("defaultScheduler")
    public TaskScheduler defaultScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("defaultScheduler-");
        scheduler.initialize();
        return scheduler;
    }

    // you can define other domain-specific scheduler, and autowired them
    // Email domain-specific scheduler
    @Bean(name = "tokenScheduler")
    public TaskScheduler tokenScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(3);
        scheduler.setThreadNamePrefix("tokenScheduler-");
        scheduler.initialize();
        return scheduler;
    }

    @Bean(name = "feedbackScheduler")
    public TaskScheduler feedbackScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("feedbackScheduler-");
        scheduler.initialize();
        return scheduler;
    }

    @Bean(name = "documentScheduler")
    public TaskScheduler documentScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("documentScheduler-");
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
            taskRegistrar.setTaskScheduler(defaultScheduler());
    }
}
