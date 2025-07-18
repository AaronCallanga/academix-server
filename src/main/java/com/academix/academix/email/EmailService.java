package com.academix.academix.email;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

// To declare contracts (methods that anyone should implement)
public interface EmailService {
    void sendEmail(String toAddress, String subject, String body) throws MessagingException, UnsupportedEncodingException;
    // can add more shared logic that will be define throguh BaseEmailServiceImpl
}
