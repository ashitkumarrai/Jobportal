package com.jobportal.system.entity;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Component
@Builder
public class Candidate {
    private User user;

    @JsonIgnoreProperties(value = {"candidatesApplied"})
    private List<Job> appliedJobs;
    private List<String> skills;
    private List<Education> education;

    private List<Certification> certification;

    private String resumeUrl;

    private List<WorkExperience> workExperiences;

    @Override
    public String toString() {
        return "Candidate [user=" + user + ", appliedJobs=" + appliedJobs + ", skills=" + skills + ", education="
                + education + ", certification=" + certification + ", resumeUrl=" + resumeUrl + ", workExperiences="
                + workExperiences + "]";
    }

  




}
