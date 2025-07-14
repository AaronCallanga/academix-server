package com.academix.academix.security.service.implementation;

import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.security.service.api.EmailService;
import com.academix.academix.user.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(User user, String link, VerificationToken token) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "aaroncallanga01@gmail.com";
        String senderName = "Academix";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);

        String verifyUrl = link.concat("/api/v1/auth/verify?token=" + token.getToken());

        mimeMessageHelper.setFrom(fromAddress, senderName);
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject(subject);
        content = content.replace("[[name]]",user.getName());
        content = content.replace("[[URL]]", verifyUrl);

        mimeMessageHelper.setText(content, true);
        mailSender.send(message);
    }
}
