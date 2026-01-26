package com.dashboard.scrumDashboard;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JiraController {
    private JiraService jiraService;

    @Value("${jira.target.board-id}")
    private Long boardId;

    public JiraController(JiraService jiraService) {
        this.jiraService = jiraService;
    }

   @GetMapping("/list-issues")
    public Map<String, Object> listIssues(){
       Long sprintId = jiraService.activeSprint(boardId);
        return jiraService.listIssues(boardId,sprintId);
   }

   @GetMapping("/get-user")
    public Map<String, Object> getCurrentUser(){
        return jiraService.getCurrentUserProfile();
   }

    @GetMapping("/active-sprint")
    public Long activeSprintId (){
        return jiraService.activeSprint(boardId);
    }

    @GetMapping("/velocity-greenhopper")
    public Map<String, Object> getVelocityChart() {;
        return jiraService.getGreenHopperVelocity(boardId);
    }
    @GetMapping("/backlog")
    public List<Map<String, Object>> getBacklog(){
         return jiraService.getBacklogOnly(boardId);
    }
}

