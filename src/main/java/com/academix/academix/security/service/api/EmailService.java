package com.academix.academix.security.service.api;

import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.user.entity.User;

public interface EmailService {
    String sendEmail(User user, String link, VerificationToken token);
}
