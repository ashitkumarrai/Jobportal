package com.jobportal.system.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Component
@Builder
public class Employer {

    private User user;

    private String company;

    private String industry;
    @Builder.Default
    private List<Job> appliedJobs = new ArrayList<>();



    public void addJob(Job appliedJob) {
        appliedJobs.add(appliedJob);
        appliedJob.setEmployer(this);
    }

    public void removeJob(Job appliedJob) {
        appliedJobs.remove(appliedJob);
        appliedJob.setEmployer(null);
    }
}
