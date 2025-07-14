package com.academix.academix.security.service.implementation;

import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.security.service.api.EmailService;
import com.academix.academix.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {



    @Override
    public String sendEmail(User user, String link, VerificationToken token) {
        return "";
    }
}
