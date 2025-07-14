package com.academix.academix.security.service.api;

import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.user.entity.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendEmail(User user, String link, VerificationToken token) throws MessagingException, UnsupportedEncodingException;
}
