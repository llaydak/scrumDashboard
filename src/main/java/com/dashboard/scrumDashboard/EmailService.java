package com.dashboard.scrumDashboard;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Resim ekiyle beraber mail atar.
     *
     * @param to
     * @param subject
     * @param text
     * @param imageBytes
     */
    public void sendEmailWithAttachment(List<String> to, String subject, String text, byte[] imageBytes) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to.toArray(new String[0])); // Virgülle ayrılmış birden fazla maili destekler
            helper.setSubject(subject);
            helper.setText(text);
            // ByteArrayResource, byte[] verisini sanki bir dosyammış gibi davranmasını sağlar
            helper.addAttachment("Sprint-Raporu.png", new ByteArrayResource(imageBytes));

            //javaMailSender.send(message);
            Files.write(Path.of("report.png"), imageBytes);
            System.out.println("Mail başarıyla gönderildi: " + to);

        } catch (MessagingException | IOException e) {
            System.err.println(" Mail gönderilirken hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}