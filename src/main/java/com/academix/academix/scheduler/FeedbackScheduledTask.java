package com.academix.academix.scheduler;

import com.academix.academix.document.feedback.service.api.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeedbackScheduledTask {

    private final TaskScheduler taskScheduler;
    private final FeedbackService feedbackService;

    public FeedbackScheduledTask(@Qualifier("feedbackScheduler") TaskScheduler taskScheduler, FeedbackService feedbackService) {
        this.taskScheduler = taskScheduler;
        this.feedbackService = feedbackService;
    }


}
