package com.dashboard.scrumDashboard;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class JiraService {

    private final RestClient restClient;

    public JiraService(
                       @Value("${jira.url}") String jiraUrl,
                       @Value("${jira.token}") String jiraToken) {

        this.restClient = RestClient.builder()
                .baseUrl(jiraUrl)
                .defaultHeader("Authorization", "Bearer " + jiraToken)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public Long activeSprint(Long boardId) {
        try {
            Map response = restClient.get()
                    .uri("/rest/agile/1.0/board/" + boardId + "/sprint?state=active")
                    .retrieve()
                    .body(Map.class);

            List<Map<String, Object>> values = (List<Map<String, Object>>) response.get("values");
            if (values != null && !values.isEmpty()) {
                Map<String, Object> activeSprint = values.get(0);
                return ((Number) activeSprint.get("id")).longValue();
            }
        } catch (Exception e) {
            System.err.println("Sprint ID bulunurken hata: " + e.getMessage());
        }
        return null;
    }
    public Map<String,Object> listIssues(Long boardId, Long sprintId) {
        JiraSearchResponse response = restClient.get()
                .uri("/rest/agile/1.0/board/" + boardId + "/sprint/" + sprintId + "/issue")
                .retrieve()
                .body(JiraSearchResponse.class);
        if (response == null || response.getIssues() == null) {
            return Map.of();
        }
        Map<String, Object> finalResponse = new HashMap<>();
        List<JiraIssue> issueList = response.getIssues();
        if (!issueList.isEmpty()) {
            JiraIssue first = issueList.get(0);

            finalResponse.put("boardName", first.getFields().getProject().getName());
            finalResponse.put("sprintName", first.getFields().getSprint().getName());
        }
        float totalSP = 0;
        float completedSP = 0;
        List<Map<String, Object>> tableData = new ArrayList<>();

        for (JiraIssue issue : issueList){
            Double sp = issue.getFields().getStoryPoint();
            if(sp == null) sp = 0.0;
            totalSP += sp;
            String status =issue.getFields().getStatus().getName();
            if("Resolved".equalsIgnoreCase(status)){
                completedSP += sp;
            }

            Map<String, Object> row = new HashMap<>();
            row.put("key",issue.getKey());
            row.put("summary",issue.getFields().getSummary());
            row.put("sp",sp);
            row.put("status",status);

            if (issue.getFields().getAssignee() != null) {
                row.put("assignee", issue.getFields().getAssignee().getDisplayName());
            } else {
                row.put("assignee", "not defined");
            }

            tableData.add(row);

        }
        finalResponse.put("totalSP", totalSP);
        finalResponse.put("completedStoryPoints", completedSP);
        finalResponse.put("issueList", tableData);

        return finalResponse;
    }
    public Map<String, Object> getCurrentUserProfile() {
        try {
            return restClient.get()
                    .uri("/rest/api/2/myself")
                    .retrieve()
                    .body(Map.class);
        } catch (Exception e) {
            System.err.println("Kullanıcı bilgisi çekilemedi: " + e.getMessage());
            return null;
        }
    }
    public Map<String, Object> getGreenHopperVelocity(Long boardId) {
        try {
            String url = "/rest/greenhopper/1.0/rapid/charts/velocity?rapidViewId=" + boardId;
            Map response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Map.class);
            return response;
        } catch (Exception e) {
            System.err.println("Velocity verisi çekilemedi: " + e.getMessage());
            return new HashMap<>();
        }
    }

    public List<Map<String, Object>> getBacklogOnly(Long boardId) {
        try {
            String fields = "key,summary,status,priority,issuetype,created";

            // Jira'dan kutuyu (Map) alıyoruz
            String url = "/rest/agile/1.0/board/" + boardId + "/backlog?fields=" + fields;

            Map<String, Object> rawResponse = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Map.class);
            if (rawResponse != null && rawResponse.containsKey("issues")) {
                return (List<Map<String, Object>>) rawResponse.get("issues");
            }
        } catch (Exception e) {
            System.err.println("Hata: " + e.getMessage());
        }
        return new ArrayList<>();
    }
    
}