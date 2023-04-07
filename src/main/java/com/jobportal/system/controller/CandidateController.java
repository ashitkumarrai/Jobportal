package com.jobportal.system.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.system.SystemApplication;
import com.jobportal.system.entity.Candidate;
import com.jobportal.system.entity.Job;
import com.jobportal.system.entity.User;
import com.jobportal.system.exceptionhandler.RecordNotFoundException;
import com.jobportal.system.jwtconfig.SecurityUtils;
import com.jobportal.system.repository.CandidateRepository;
import com.jobportal.system.repository.EmployerRepository;
import com.jobportal.system.repository.JobRepository;
import com.jobportal.system.repository.UserRepository;

import jakarta.validation.Valid;


@RestController
public class CandidateController {

    @Autowired
    CandidateRepository cr;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    JobRepository jobRepository;

    @GetMapping("/admin/candidates")
    public List<Candidate> findAll() {
        return cr.findAll();

    }

    @PostMapping("/candidate/signup")
    public ResponseEntity candidateSignUp(@RequestBody @Valid Candidate candidate) throws URISyntaxException {

        User user = User.builder().id(SystemApplication.idautoPlus++).firstName(candidate.getUser().getFirstName())
                .lastName(candidate.getUser().getLastName()).username(candidate.getUser().getUsername())
                .email(candidate.getUser().getEmail()).contact(candidate.getUser().getContact())
                .adress(candidate.getUser().getAdress()).enabled(true)
                .password(passwordEncoder.encode(candidate.getUser().getPassword()))
                .roles(SystemApplication.candidateRole).build();

        Candidate candidateDto = Candidate.builder().user(user).appliedJobs(new ArrayList<>())
                .skills(new ArrayList<>()).workExperiences(new ArrayList<>())
                .certification(new ArrayList<>()).education(new ArrayList<>()).build();



       

        // cascading to user

        return ResponseEntity.created(new URI("/admin/candidate/" + candidate.getUser().getId()))
                .body(cr.saveOrUpdate(candidateDto).get());

    }

    @PatchMapping("/candidate/profile")
    public ResponseEntity<Object> updateProfile(@RequestBody Candidate candidate)
            throws URISyntaxException, RecordNotFoundException {

        Optional<String> username = SecurityUtils.getCurrentUserLogin();

        Map<String, Object> hashMap = new HashMap<>();

        Optional<User> userr = userRepository.findByUsername(username.get());
        if (!userr.isPresent()) {
            throw new RecordNotFoundException("logged in User not found");

        }
       
        Optional<Candidate> result = cr.updateCandidateById(userr.get().getId(), candidate);

        hashMap.put("Response", result.get());
        hashMap.put("URI", new URI("/admin/candidate/" + result.get().getUser().getId()));

        return new ResponseEntity<>(hashMap, HttpStatus.CREATED);

    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getProfile()
            throws RecordNotFoundException {

        Optional<String> username = SecurityUtils.getCurrentUserLogin();

        Optional<User> userr = userRepository.findByUsername(username.get());
        if (!userr.isPresent()) {
            throw new RecordNotFoundException("logged in User not found");

        }

        if (SecurityUtils.hasCurrentUserThisAuthority("CANDIDATE")) {
            return new ResponseEntity<>(cr.findByUsername(username.get())
                    .orElseThrow(() -> new RecordNotFoundException("profile not found in db")), HttpStatus.OK);

        }

        else if (SecurityUtils.hasCurrentUserThisAuthority("ADMIN")) {
            return new ResponseEntity<>(userRepository.findByUsername(username.get())
                    .orElseThrow(() -> new RecordNotFoundException("profile not found in db")), HttpStatus.OK);
        } else if (SecurityUtils.hasCurrentUserThisAuthority("EMPLOYER")) {
            return new ResponseEntity<>(employerRepository.findById(userr.get().getId())
                    .orElseThrow(() -> new RecordNotFoundException("profile not found in db")), HttpStatus.OK);

        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/candidate/applyjob/{id}")
    public ResponseEntity<Object> applyJob(@PathVariable Long id)
            throws RecordNotFoundException {
        Optional<String> username = SecurityUtils.getCurrentUserLogin();

        
        
        Candidate can = cr.findByUsername(
                username.orElseThrow(() -> new RecordNotFoundException("record not found in db")))
                .orElseThrow(() -> new RecordNotFoundException("record not found in db"));
        Optional.ofNullable(can.getResumeUrl()).orElseThrow(() -> new RecordNotFoundException(
                "CV/resume of logged in candidate not found,  submit cv before appliying"));



         if(Optional.ofNullable(can.getAppliedJobs()).isPresent())
        if (can.getAppliedJobs().stream().anyMatch(job -> job.getId().equals(id))) {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("Response", "Candidate has already applied !");
            return new ResponseEntity(hashMap,HttpStatus.NOT_ACCEPTABLE);
        }
        
              
                Job job = jobRepository.findById(id)
                        .orElseThrow(() -> new RecordNotFoundException("record not found in db"));
                
                can.getAppliedJobs().add(job);
                List<Candidate> lst = new ArrayList<>();
                lst.addAll(job.getCandidatesApplied());
                lst.add(can);
                for (Job j : SystemApplication.jobs) {
                    if (j.getId().equals(id)) {
                        

                        j.setCandidatesApplied(lst);
                    }
               }
                        
                

        return ResponseEntity.accepted().body(can);
    }




}
