package com.jobportal.system.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.system.SystemApplication;
import com.jobportal.system.entity.Employer;
import com.jobportal.system.entity.Job;
import com.jobportal.system.entity.User;
import com.jobportal.system.exceptionhandler.RecordNotFoundException;
import com.jobportal.system.jwtconfig.SecurityUtils;
import com.jobportal.system.repository.EmployerRepository;
import com.jobportal.system.repository.JobRepository;
import com.jobportal.system.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@RestController

@Log4j2
public class EmployerController {

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserRepository userRepository;



    @PostMapping("employer/createjob")
    public ResponseEntity<Object> createJob(@RequestBody Job job) throws URISyntaxException, RecordNotFoundException {

        Job jobDto = Job.builder().id(SystemApplication.idautoPlus++).title(job.getTitle()).description(job.getDescription())
                .location(job.getLocation()).jobType(job.getJobType()).requirements(job.getRequirements()).build();

       String username = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new RecordNotFoundException("logged in User not found"));

        User userr = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("logged in User not found"));
        
        
        Employer employer = employerRepository.findById(userr.getId())
                .orElseThrow(() -> new RecordNotFoundException("logged in User not found"));
                jobDto.setEmployer(employer);
        
        return ResponseEntity.created(new URI("employer/"+employer.getUser().getId())).body(jobRepository.saveOrUpdate(jobDto));

    }
    
}
