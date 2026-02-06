package com.dashboard.scrumDashboard.controller;
import com.dashboard.scrumDashboard.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dashboard.scrumDashboard.ReportRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import java.util.Base64;

    @RestController
    @CrossOrigin(origins = "http://localhost:3000")
    public class ReportController {

        @Autowired
        private EmailService emailService;

        @PostMapping("/report-base64")
        public ResponseEntity<String> receiveReport(@Valid @RequestBody ReportRequest request) {
            final byte[] imageBytes;
        try {
            imageBytes = Base64.getDecoder().decode(request.getImage());
        } catch (IllegalArgumentException e){
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
            emailService.sendEmailWithAttachment(
                    request.getRecipients(),
                    request.getSubject() != null && !request.getSubject().isBlank()
                            ? request.getSubject()
                            : "Günlük Sprint Raporu",
                    "Merhaba, güncel rapor ektedir.",
                    imageBytes
            );

            return ResponseEntity.ok("Başarılı");
        }
    }