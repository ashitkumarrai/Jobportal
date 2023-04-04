package com.jobportal.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.system.entity.Job;
import com.jobportal.system.repository.JobRepository;

@RestController
public class JobController {

    @Autowired
    JobRepository jobRepository;
    
    @GetMapping("/show/jobs")
    public List<Job> findAll() {
        return jobRepository.findAll();

        

    }



    
}
