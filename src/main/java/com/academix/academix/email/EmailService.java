package com.academix.academix.email;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendEmail(String toAddress, String subject, String body) throws MessagingException, UnsupportedEncodingException;
}
