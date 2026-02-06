package com.dashboard.scrumDashboard;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ReportRequest {

        @NotBlank
        private String image;
        @NotEmpty
        private List<@Email String> recipients;
        private String subject;

}