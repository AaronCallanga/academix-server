package com.academix.academix.scheduler;

import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.feedback.repository.FeedbackRepository;
import com.academix.academix.document.feedback.service.api.FeedbackService;
import com.academix.academix.email.api.FeedbackEmailService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Component
@Slf4j
public class FeedbackScheduledTask {

    private final TaskScheduler feedbackScheduler;
    private final FeedbackEmailService feedbackEmailService;
    private final FeedbackRepository feedbackRepository;

    public FeedbackScheduledTask(@Qualifier("feedbackScheduler") TaskScheduler feedbackScheduler,
                                 FeedbackEmailService feedbackEmailService, FeedbackRepository feedbackRepository) {
        this.feedbackScheduler = feedbackScheduler;
        this.feedbackEmailService = feedbackEmailService;
        this.feedbackRepository = feedbackRepository;
    }

    @PostConstruct
    public void init() {
        feedbackScheduler.scheduleAtFixedRate(this::sendFeedbackEmailReminder, );
    }

    private void sendFeedbackEmailReminder() {

        feedbackEmailService.notifyFeedbackReminder(user, feedback);
    }
}
