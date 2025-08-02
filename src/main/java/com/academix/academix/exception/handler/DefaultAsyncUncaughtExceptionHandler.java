package com.academix.academix.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class DefaultAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultAsyncUncaughtExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        logger.error("Unhandled async error in method: {}", method.getName());
        logger.error("Parameters: {}", Arrays.toString(params));
        logger.error("Exception message: {}", ex.getMessage(), ex);
        // Optional: integrate with external services like email/Sentry/Slack
        // notifyMonitoringService(method, ex);
    }

    // private void notifyMonitoringService(Method method, Throwable ex) {
    //     // Implement integration with monitoring/alert system
    // }
}

/*
Do not swallow exceptions silently in async code. Always implement AsyncUncaughtExceptionHandler for void async methods.
For @Async methods returning Future or CompletableFuture, prefer using .exceptionally() or try/catch.
 */
