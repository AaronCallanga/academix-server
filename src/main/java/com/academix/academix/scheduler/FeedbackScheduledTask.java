package com.academix.academix.scheduler;

import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.feedback.repository.FeedbackRepository;
import com.academix.academix.document.feedback.service.api.FeedbackService;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.repository.DocumentRequestRepository;
import com.academix.academix.email.api.FeedbackEmailService;
import com.academix.academix.user.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Component
@Slf4j
public class FeedbackScheduledTask {

    private final TaskScheduler feedbackScheduler;
    private final FeedbackEmailService feedbackEmailService;
    private final DocumentRequestRepository documentRequestRepository;

    public FeedbackScheduledTask(@Qualifier("feedbackScheduler") TaskScheduler feedbackScheduler,
                                 FeedbackEmailService feedbackEmailService,
                                 DocumentRequestRepository documentRequestRepository) {
        this.feedbackScheduler = feedbackScheduler;
        this.feedbackEmailService = feedbackEmailService;
        this.documentRequestRepository = documentRequestRepository;
    }

    @PostConstruct
    public void init() {
        feedbackScheduler.scheduleAtFixedRate(this::sendFeedbackEmailReminder, Duration.ofDays(3)); // 24 hours or 2/3 days
    }

    private void sendFeedbackEmailReminder() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(3);

        List<DocumentRequest> documentRequests = documentRequestRepository.findRequestCompletedWithoutFeedback(cutoffDate);

        for (DocumentRequest documentRequest : documentRequests) {
            User user = documentRequest.getRequestedBy();
            feedbackEmailService.notifyFeedbackReminder(user, documentRequest);
            log.info("Sending feedback reminder to {}", user.getEmail());
            log.info("Running scheduled feedback reminder at {}", LocalDateTime.now());
        }

    }
}
