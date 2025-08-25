package com.academix.academix.email.api;

import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.email.EmailService;
import com.academix.academix.user.entity.User;

public interface FeedbackEmailService extends EmailService {
    void sendLowRatingSupport(User user, Feedback feedback);
    void notifyAppreciation(User user, Feedback feedback);
    void notifyNeutralAcknowledgement(User user, Feedback feedback);
    void notifyFeedbackReminder(User user, Feedback feedback);
}
