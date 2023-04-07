package com.jobportal.system.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    @JsonIgnoreProperties(value = {"employer"})
    private List<Job> createdJobs = new ArrayList<>();



  
}
