package com.academix.academix.email;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
