package com.dashboard.scrumDashboard.controller;
import com.dashboard.scrumDashboard.RendererClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@RestController
@RequestMapping("/internal")
public class ReportDebugController {

    private final RendererClient rendererClient;
    private final String reportPageUrl;

    public ReportDebugController(RendererClient rendererClient,
                                 @Value("${report.page.url}") String reportPageUrl) {
        this.rendererClient = rendererClient;
        this.reportPageUrl = reportPageUrl;
    }
    @GetMapping(value="/report.png", produces=MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> renderReportPng() throws IOException {
        byte[] png = rendererClient.renderPng(reportPageUrl);

        Files.createDirectories(Path.of("reports"));
        Path out = Path.of("reports", "report-" + System.currentTimeMillis() + ".png");
        Files.write(out, png);

        return ResponseEntity.ok(png);
    }
}