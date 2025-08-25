package com.academix.academix.email.impl;

import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.email.BaseEmailServiceImpl;
import com.academix.academix.email.api.FeedbackEmailService;
import com.academix.academix.user.entity.User;
import org.springframework.mail.javamail.JavaMailSender;

public class FeedbackEmailServiceImpl extends BaseEmailServiceImpl implements FeedbackEmailService {

    public FeedbackEmailServiceImpl(JavaMailSender mailSender) {
        super(mailSender);
    }

    @Override
    public void sendLowRatingSupport(User user, Feedback feedback) {

    }

    @Override
    public void sendAppreciation(User user, Feedback feedback) {

    }

    @Override
    public void sendNeutralAcknowledgement(User user, Feedback feedback) {

    }

    @Override
    public void sendFeedbackReminder(User user, Feedback feedback) {

    }
}
