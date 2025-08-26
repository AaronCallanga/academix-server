package com.academix.academix.email.impl;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.email.BaseEmailServiceImpl;
import com.academix.academix.email.api.DocumentEmailService;
import com.academix.academix.user.entity.User;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class DocumentEmailServiceImpl extends BaseEmailServiceImpl implements DocumentEmailService {

    public DocumentEmailServiceImpl(JavaMailSender mailSender) {
        super(mailSender);
    }

    @Async("emailExecutor")
    @Override
    public void notifyDocumentUpdate(User user, DocumentRequest documentRequest) {
        String toAddress = user.getEmail();
        String subject = "Document Request Update";
        String content = "Dear [[name]],<br><br>"
                + "We would like to inform you that the status of your document request "
                + "(<b>Request ID: [[requestId]]</b>) has been updated.<br><br>"
                + "<b>Current Status:</b> [[status]]<br>"
                + "<b>Requested Document:</b> [[documentType]]<br><br>"
                + "You may log in to your Academix account for more details.<br><br>"
                + "Thank you for using Academix.<br><br>"
                + "Best regards,<br>"
                + "<b>Academix Team</b>";

        // Replace placeholders
        content = content.replace("[[name]]", user.getName())
            .replace("[[requestId]]", String.valueOf(documentRequest.getId()))
            .replace("[[status]]", documentRequest.getStatus().name()) // assuming Enum status
            .replace("[[documentType]]", documentRequest.getDocumentType().name());

       sendEmail(toAddress, subject, content);
    }

    @Async("emailExecutor")
    @Override
    public void notifyDocumentComplete(User user, DocumentRequest documentRequest) {
        String toAddress = user.getEmail();
        String subject = "Your Document Request is Picked Up!";
        String content = "Dear [[name]],<br>"
                + "Good news! Your document request (ID: [[requestId]]) has been completed.<br>"
                + "Please don’t forget to provide us with feedback to help improve our services.<br>"
                + "<br><a href=\"[[feedbackURL]]\">Leave Feedback</a><br><br>"  // sample url, if you got frontend
                + "Thank you,<br>"
                + "Academix Team";

        content = content.replace("[[name]]", user.getName())
                         .replace("[[requestId]]", documentRequest.getId().toString())
                         .replace("[[feedbackURL]]", "https://academix.com/feedback/" + documentRequest.getId());

        sendEmail(toAddress, subject, content);
    }

    @Async("emailExecutor")
    @Override
    public void notifyDocumentRequestSubmitted(User user, DocumentRequest documentRequest) {
        String toAddress = user.getEmail();
        String subject = "Your Document Request Has Been Submitted";
        String content = "Dear [[name]],<br>"
                + "We have received your document request (ID: [[requestId]]).<br>"
                + "Our team will review it shortly and keep you updated.<br>"
                + "<br>You can always track the status by logging in to Academix.<br>"
                + "<br>Thank you,<br>"
                + "Academix Team";

        content = content.replace("[[name]]", user.getName())
                         .replace("[[requestId]]", documentRequest.getId().toString());

        sendEmail(toAddress, subject, content);
    }

    @Override
    public void sendReminder(User user, DocumentRequest documentRequest) {
        String toAddress = user.getEmail();
        String subject = "Reminder: Your Document is Ready for Pickup";
        String content = "Dear [[name]],<br><br>"
                + "This is a friendly reminder that your requested document "
                + "(<b>Request ID: [[requestId]]</b>, [[documentType]]) "
                + "is scheduled for pickup on <b>[[pickUpDate]]</b>.<br><br>"
                + "Current Status: <b>[[status]]</b><br><br>"
                + "Please make sure to pick it up at the registrar’s office.<br><br>"
                + "Thank you,<br>"
                + "<b>Academix Team</b>";

        // Replace placeholders
        content = content.replace("[[name]]", user.getName())
                         .replace("[[requestId]]", String.valueOf(documentRequest.getId()))
                         .replace("[[status]]", documentRequest.getStatus().name())
                         .replace("[[documentType]]", documentRequest.getDocumentType().name())
                         .replace("[[pickUpDate]]", documentRequest.getPickUpDate().toLocalDate().toString());

        sendEmail(toAddress, subject, content);
    }
}
