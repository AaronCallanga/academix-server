package com.academix.academix.email.impl;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.email.BaseEmailServiceImpl;
import com.academix.academix.email.api.DocumentEmailService;
import com.academix.academix.user.entity.User;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class DocumentEmailServiceImpl extends BaseEmailServiceImpl implements DocumentEmailService {

    public DocumentEmailServiceImpl(JavaMailSender mailSender) {
        super(mailSender);
    }

    @Override
    public void sendDocumentUpdate(User user, DocumentRequest documentRequest) throws MessagingException, UnsupportedEncodingException {
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

    @Override
    public void sendDocumentComplete(User user,
                                     DocumentRequest documentRequest) throws MessagingException, UnsupportedEncodingException {

    }

    @Override
    public void sendDocumentRequestSubmitted(User user,
                                             DocumentRequest documentRequest) throws MessagingException, UnsupportedEncodingException {

    }
}
