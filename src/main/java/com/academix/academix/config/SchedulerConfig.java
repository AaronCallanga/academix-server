package com.academix.academix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

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
    @Bean(name = "emailScheduler")
    public TaskScheduler emailScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(3);
        scheduler.setThreadNamePrefix("emailScheduler-");
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
            taskRegistrar.setTaskScheduler(defaultScheduler());
    }
}
