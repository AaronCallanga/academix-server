package com.academix.academix.email;

import com.academix.academix.exception.types.EmailSendFailureException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseEmailServiceImpl implements EmailService {

    protected final JavaMailSender mailSender;      //private does not allow subclasses to access it

    @Override
    public void sendEmail(String toAddress, String subject, String body) {
        try {
            String fromAddress = "aaroncallanga01@gmail.com";
            String senderName = "Academix";
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setFrom(fromAddress, senderName);
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);
            mailSender.send(mimeMessage);
            log.info("Email sent to {}", toAddress);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error("Failed to send email to {}: {}", toAddress, ex.getMessage());
            throw new EmailSendFailureException("Failed to send email", ex);
        }
    }

    // render template
}
