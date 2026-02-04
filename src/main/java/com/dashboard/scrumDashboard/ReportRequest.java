package com.dashboard.scrumDashboard;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

class ReportRequest {

        @NotBlank
        private String image;
        @NotEmpty
        private List<@Email String> recipients;
        private String subject;

        public List<String> getRecipients() {
                return recipients;
        }

        public void setRecipients(List<String> recipients) {
                this.recipients = recipients;
        }

        public String getSubject() {
                return subject;
        }

        public void setSubject(String subject) {
                this.subject = subject;
        }

        public String getImage() { return image; }
        public void setImage(String image) { this.image = image; }

    }