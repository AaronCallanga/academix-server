package com.academix.academix.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
public abstract class BaseEmailServiceImpl implements EmailService {

    protected final JavaMailSender mailSender;      //private does not allow subclasses to access it

    @Override
    public void sendEmail(String toAddress, String subject, String body) throws MessagingException, UnsupportedEncodingException {
        String fromAddress = "aaroncallanga01@gmail.com";
        String senderName = "Academix";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(fromAddress, senderName);
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, true);
        mailSender.send(mimeMessage);
    }
}
