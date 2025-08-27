package com.academix.academix.email.api;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.email.EmailService;
import com.academix.academix.user.entity.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface DocumentEmailService extends EmailService {

    // Status changed
    void notifyDocumentUpdate(User user, DocumentRequest documentRequest);

    // Request claimed, send feedback reminder
    void notifyDocumentComplete(User user, DocumentRequest documentRequest);

    // Request has been submitted
    void notifyDocumentRequestSubmitted(User user, DocumentRequest documentRequest);

    // Send Reminder
    void sendReminder(User user, DocumentRequest documentRequest);
}
