package com.dashboard.scrumDashboard.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender mailSender) {
        this.emailSender = mailSender;
    }

    public void sendEmailWithAttachment(List<String> recipients,
                                        String subject,
                                        String text,
                                        byte[] imageBytes) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(recipients.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(text, false);

            helper.addAttachment("Sprint-Raporu.png", new ByteArrayResource(imageBytes));
            emailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Mail send failed", e);
        }
    }
}
