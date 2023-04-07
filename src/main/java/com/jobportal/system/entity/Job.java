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
public class Job {
    private Long id;

    private String title;

    private String description;

    private String location;

    private String jobType;

   

    //skills

    private List<String> requirements;


    //transient

    private Long totalCandidatesApplied;

    public Long getTotalCandidatesApplied() {
        return (long) candidatesApplied.size();
    }


    
   @JsonIgnoreProperties({"appliedJobs"})
    private Employer employer;
     @JsonIgnoreProperties(value={"appliedJobs","workExperiences","resumeUrl","certification","education","skills"})
    private List<Candidate> candidatesApplied;

    @Override
    public String toString() {
        return "Job [id=" + id + ", title=" + title + ", description=" + description + ", location=" + location
                + ", jobType=" + jobType + "totalCandidatesApplied "+totalCandidatesApplied + "]";
    }


    
}
