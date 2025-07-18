package com.academix.academix.email.api;

import com.academix.academix.email.EmailService;
import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.user.entity.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface AuthEmailService extends EmailService {
    void sendVerification(User user, String link, VerificationToken token) throws MessagingException, UnsupportedEncodingException;
}
