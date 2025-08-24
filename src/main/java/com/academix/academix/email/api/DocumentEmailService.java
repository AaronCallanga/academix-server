package com.academix.academix.email.api;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.email.EmailService;
import com.academix.academix.user.entity.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface DocumentEmailService extends EmailService {

    // Status changed
    void sendDocumentUpdate(User user, DocumentRequest documentRequest);

    // Request claimed, send feedback reminder
    void sendDocumentComplete(User user, DocumentRequest documentRequest);

    // Request has been submitted
    void sendDocumentRequestSubmitted(User user, DocumentRequest documentRequest);
}
