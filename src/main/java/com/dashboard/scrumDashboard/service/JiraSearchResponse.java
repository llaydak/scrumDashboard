package com.dashboard.scrumDashboard.service;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Bilmediğimiz alanları görmezden gel
public class JiraSearchResponse {
    private List<JiraIssue> issues; // İşlerin listesi
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class JiraIssue {
    private String key;   // Örn: KRA-1
    private JiraFields fields; // Detaylar kutusu
}

// 3. DETAY KUTUSU
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class JiraFields {
    private JiraStatus status;
    private String summary;
    private JiraAssignee assignee;
    @JsonProperty("customfield_10028")
    private Double storyPoint;
    private JiraSprint sprint;
    private JiraProject project;
}

// 4. STATÜ KUTUSU (Bize lazım olan "Done", "To Do" yazısı burada)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class JiraStatus {
    private String name; // Örn: "Done"
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class JiraAssignee{
    private String displayName;
    private String emailAddress;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class JiraProject{
    private String name;
    private String key;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class JiraSprint{
    private String name;
    private int id;
}


