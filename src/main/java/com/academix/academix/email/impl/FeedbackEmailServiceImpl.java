package com.academix.academix.email.impl;

import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.email.BaseEmailServiceImpl;
import com.academix.academix.email.api.FeedbackEmailService;
import com.academix.academix.user.entity.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

public class FeedbackEmailServiceImpl extends BaseEmailServiceImpl implements FeedbackEmailService {

    public FeedbackEmailServiceImpl(JavaMailSender mailSender) {
        super(mailSender);
    }

    @Async("emailExecutor")
    @Override
    public void sendLowRatingSupport(User user, Feedback feedback) {
        String toAddress = user.getEmail();
        String subject = "We’re Sorry to Hear About Your Experience";
        String content = "Dear [[name]],<br><br>"
                + "We noticed that you rated your recent document request (ID: [[requestId]]) with a score of <b>[[rating]]</b>.<br>"
                + "We’re sorry that your experience didn’t meet expectations.<br><br>"
                + "Our support team would love to hear from you and help resolve any issues you faced.<br>"
                + "Please reach out to us at <a href=\"mailto:aaroncallanga@gmail.com\">aaroncallanga@gmail.com</a>.<br><br>" // support@academix.com
                + "Thank you for helping us improve our services.<br><br>"
                + "Best regards,<br>"
                + "<b>Academix Support Team</b>";

        content = content.replace("[[name]]", user.getName())
                         .replace("[[requestId]]", feedback.getDocumentRequest().getId().toString())
                         .replace("[[rating]]", String.valueOf(feedback.getRating()));

        sendEmail(toAddress, subject, content);
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
