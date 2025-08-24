package com.academix.academix.email.impl;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.email.BaseEmailServiceImpl;
import com.academix.academix.email.api.DocumentEmailService;
import com.academix.academix.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DocumentEmailServiceImpl extends BaseEmailServiceImpl implements DocumentEmailService {

    public DocumentEmailServiceImpl(JavaMailSender mailSender) {
        super(mailSender);
    }

    @Override
    public void sendDocumentUpdate(User user, DocumentRequest documentRequest) {

    }
}
