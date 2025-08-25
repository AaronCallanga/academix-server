package com.academix.academix.email.api;

import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.email.EmailService;
import com.academix.academix.user.entity.User;

public interface FeedbackEmailService extends EmailService {
    void sendLowRatingSupport(User user, Feedback feedback);
    void sendAppreciation(User user, Feedback feedback);
    void sendNeutralAcknowledgement(User user, Feedback feedback);
    void sendFeedbackReminder(User user, Feedback feedback);
}
