package com.dashboard.scrumDashboard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RendererClient {
    private final RestTemplate restTemplate;
    private final String rendererBaseUrl;

    public RendererClient(RestTemplate restTemplate,
                          @Value("${renderer.url}") String rendererBaseUrl) {
        this.restTemplate = restTemplate;
        this.rendererBaseUrl = rendererBaseUrl;
    }

    public byte[] renderPng(String pageUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity =
                new HttpEntity<>(Map.of("url", pageUrl), headers);

        ResponseEntity<byte[]> resp = restTemplate.exchange(
                rendererBaseUrl + "/render/png",
                HttpMethod.POST,
                entity,
                byte[].class
        );

        if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
            throw new RuntimeException("Renderer failed: " + resp.getStatusCode());
        }
        return resp.getBody();
    }
}
