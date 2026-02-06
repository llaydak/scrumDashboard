package com.dashboard.scrumDashboard.service;

import com.dashboard.scrumDashboard.RendererClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportSendService {

    private final RendererClient rendererClient;
    private final EmailService emailService;
    private final String reportPageUrl;

    public ReportSendService(RendererClient rendererClient,
                             EmailService emailService,
                             @Value("${report.page.url}") String reportPageUrl) {
        this.rendererClient = rendererClient;
        this.emailService = emailService;
        this.reportPageUrl = reportPageUrl;
    }

    public void sendDailyReport(List<String> recipients) {
        byte[] png = rendererClient.renderPng(reportPageUrl);
        emailService.sendEmailWithAttachment(
                recipients,
                "Automated Sprint Report",
                "Merhaba, güncel rapor ektedir.",
                png
        );
    }
}
