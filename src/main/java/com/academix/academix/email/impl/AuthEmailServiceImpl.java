package com.academix.academix.email.impl;

import com.academix.academix.email.BaseEmailServiceImpl;
import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.email.api.AuthEmailService;
import com.academix.academix.user.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
//@Slf4j
public class AuthEmailServiceImpl extends BaseEmailServiceImpl implements AuthEmailService {

    public AuthEmailServiceImpl(JavaMailSender mailSender) {
        super(mailSender);
    }

    @Async("emailExecutor")
    @Override
    public void sendVerification(User user, String link, VerificationToken token){
        String toAddress = user.getEmail();
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Academix";

        String verifyUrl = link.concat("/api/v1/auth/verify?token=" + token.getToken());

        content = content.replace("[[name]]",user.getName());
        content = content.replace("[[URL]]", verifyUrl);

        sendEmail(toAddress, subject, content);
        //log.info("Sending email on thread {}", Thread.currentThread().getName());     // For testing
        //throw new RuntimeException("Test Exception");
    }
}
