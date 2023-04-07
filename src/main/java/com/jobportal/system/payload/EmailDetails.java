package com.jobportal.system.payload;


import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Java Program to Illustrate EmailDetails Class

 
// Importing required classes
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
 
// Class
@Component
public class EmailDetails {
 
    // Class data members
    @NotBlank(message = "email is mandatory")
	@Email(message = "Email should be valid")
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
