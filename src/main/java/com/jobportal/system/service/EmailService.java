package com.jobportal.system.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jobportal.system.payload.EmailDetails;


@Service
public interface EmailService {
    ResponseEntity<Map<String,String>>  sendSimpleMail(EmailDetails details);
 
    // Method
    // To send an email with attachment
    ResponseEntity<Map<String,String>>  sendMailWithAttachment(EmailDetails details);
}
