package com.jobportal.system.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import jakarta.validation.Valid;
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

        @Autowired
        PasswordEncoder passwordEncoder;

        @PostMapping("employer/createjob")
        public ResponseEntity<Object> createJob(@RequestBody Job job)
                        throws URISyntaxException, RecordNotFoundException {

                Job jobDto = Job.builder().id(SystemApplication.idautoPlus++).title(job.getTitle())
                                .description(job.getDescription())
                                .location(job.getLocation()).jobType(job.getJobType())
                                .requirements(job.getRequirements()).candidatesApplied(new ArrayList<>())
                                .build();

                String username = SecurityUtils.getCurrentUserLogin()
                                .orElseThrow(() -> new RecordNotFoundException("logged in User not found"));

                User userr = userRepository.findByUsername(username)
                                .orElseThrow(() -> new RecordNotFoundException("logged in User not found"));

                Employer employer = employerRepository.findById(userr.getId())
                                .orElseThrow(() -> new RecordNotFoundException("logged in User not found"));
                jobDto.setEmployer(employer);

                for (Employer emp : SystemApplication.employers) {
                        if (emp.getUser().getUsername().equals(employer.getUser().getUsername())) {

                                if (Optional.ofNullable(emp.getCreatedJobs()).isPresent()) {
                                        ArrayList<Job> temp = new ArrayList<>();
                                        temp.addAll(emp.getCreatedJobs());
                                        temp.add(jobDto);
                                        emp.setCreatedJobs(temp);
                                }

                                else {
                                        emp.setCreatedJobs(new ArrayList(List.of(jobDto)));
                                }
                        }
                }
                jobRepository.saveOrUpdate(jobDto);

                return ResponseEntity.created(new URI("employer/" + employer.getUser().getId()))
                                .body(employerRepository.findById(userr.getId()));

        }

        @PostMapping("/employer/signup")
        public ResponseEntity employerSignUp(@RequestBody @Valid Employer employer) throws URISyntaxException {
                User user = User.builder().id(SystemApplication.idautoPlus++)
                                .firstName(employer.getUser().getFirstName()).lastName(employer.getUser().getLastName())
                                .username(employer.getUser().getUsername()).email(employer.getUser().getEmail())
                                .contact(employer.getUser().getContact()).adress(employer.getUser().getAdress())
                                .enabled(true).password(passwordEncoder.encode(employer.getUser().getPassword()))
                                .roles(SystemApplication.employerRole).build();
                Employer employerDto = Employer.builder().user(user).createdJobs(new ArrayList<>())
                                .company(employer.getCompany()).industry(employer.getIndustry()).build();
                // cascading to user
                return ResponseEntity.created(new URI("/admin/employer/" + employer.getUser().getId()))
                                .body(employerRepository.saveOrUpdate(employerDto).get());
        }

}
