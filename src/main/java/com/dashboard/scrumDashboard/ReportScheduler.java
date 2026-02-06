package com.dashboard.scrumDashboard;

import com.dashboard.scrumDashboard.service.ReportSendService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableScheduling
@Component
public class ReportScheduler {

    private final ReportSendService reportSendService;

    public ReportScheduler(ReportSendService reportSendService) {
        this.reportSendService = reportSendService;
    }

    @Scheduled(cron = "0 0 10 * * *", zone = "Europe/Istanbul") // her gün 09:00
    public void run() {
        reportSendService.sendDailyReport(List.of("ilaaydakdag@gmail.com"));
    }
}

