package com.jobportal.system.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jobportal.system.SystemApplication;
import com.jobportal.system.entity.Employer;
import com.jobportal.system.entity.Job;
import com.jobportal.system.entity.User;
import com.jobportal.system.entity.VerificationToken;
import com.jobportal.system.exceptionhandler.RecordNotFoundException;
import com.jobportal.system.jwtconfig.SecurityUtils;
import com.jobportal.system.payload.EmailDetails;
import com.jobportal.system.repository.EmployerRepository;
import com.jobportal.system.repository.JobRepository;
import com.jobportal.system.repository.UserRepository;
import com.jobportal.system.service.EmailService;

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

        @Autowired
        EmailService emailService;


        @Value("${spring.mail.username}")
               private String sender;

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
                                .password(passwordEncoder.encode(employer.getUser().getPassword()))
                                .roles(SystemApplication.employerRole).imageUrl(employer.getUser().getImageUrl()).build();
                Employer employerDto = Employer.builder().user(user).createdJobs(new ArrayList<>())
                                .company(employer.getCompany()).industry(employer.getIndustry()).build();
                


                //Sending email to admin with activation link

                //link https://localhost:8080/verify/email/?  token=t8kcpMSyeZTv8m1aeiD-GVZgiNz7rgjW4W88XLDz1rc



                String token = UUID.randomUUID().toString();
                VerificationToken vt = new VerificationToken(user.getId(),token);
        
                user.setToken(vt);


                // for Admin Approval, Email will be send to admin email i'd

        EmailDetails emailDetails = new EmailDetails();

        emailDetails.setRecipient(sender);
        emailDetails.setSubject("New Employer has Registered");

        MultiValueMap<String, String> urlParams = new LinkedMultiValueMap<>();

        urlParams.add("id", employerDto.getUser().getId().toString());
        urlParams.add("token", user.getToken().getToken());

        URI loc = ServletUriComponentsBuilder.fromCurrentContextPath().path("/verify/email").queryParams(urlParams)
                .buildAndExpand().toUri();

        String approvalLink = loc.toString();
        emailDetails.setMsgBody(
                "Hi Admin,\n New Employer has register! \n To Approve and enable the registration: "
                        + "\n\n" + "Full Name : " + employerDto.getUser().getFirstName()
                        + "\n\n" + "Id : " + employerDto.getUser().getId()
                        + "\n\n" + "Contact: " + employerDto.getUser().getContact()
                        + "\n\n" + "Email id : " + employerDto.getUser().getEmail()
                        + "\n\n To Approve click on this approval link: " + approvalLink);

        emailService.sendSimpleMail(emailDetails);







        
        





                return ResponseEntity.created(new URI("/admin/employer/" + employer.getUser().getId()))
                                .body(employerRepository.saveOrUpdate(employerDto).get());
        }



  @GetMapping(value="/verify/email")
    public ResponseEntity<Object> doApprove(@RequestParam("id") Long id, @RequestParam("token") String token) throws RecordNotFoundException {
        //verificaton link generating to send this link to admin mail to enable doctor
        User tempUser = null;
        tempUser = userRepository.findByTokenToken(token)
                .orElseThrow(() -> new RecordNotFoundException("token is not found in db"));

        
                // Employer tempEmp = employerRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Employer is not found in db"));
        Map<String, String> r = new HashMap<>();

        if (tempUser != null ) {
            tempUser.setEnabled(true);
           // employerRepository.saveOrUpdate(tempEmp);
            r.put("Response", " Registeration Approved.");
            return new ResponseEntity<Object>(r, HttpStatus.OK);
        } else {
            r.put("Response", "id & token doesn't matched!");
            return new ResponseEntity<Object>(r, HttpStatus.BAD_REQUEST);
        }

    }

}
