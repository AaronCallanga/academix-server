package com.academix.academix.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

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
