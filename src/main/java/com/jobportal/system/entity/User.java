package com.jobportal.system.entity;



import java.util.Set;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor

@Component
@Builder
public class User {
	
	private Long id;

    
	
    
    

	@Pattern(regexp = "([\\w_\\.]){3,15}", message = " username must be alpha-numeric [can contains underscore(_)or dot(.) and @]")
	
	private String username;

	private String firstName;

	private String lastName;


	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;




	

        
	@Email(message = "Email should be valid")

	private String email;


	@Pattern(regexp="^[2-9]{2}\\d{8}$",message= "phone number not valid")
    private String contact;
	
	
	private String imageUrl;

	
	@JsonProperty(access = Access.WRITE_ONLY)
  

	@Valid
	private Set<Role> roles;
	
	
	//@JsonProperty(access = Access.WRITE_ONLY)
	//for extra email event 
    private boolean enabled;
    
	private String adress;
	
	
	
	
	@JsonProperty(access = Access.WRITE_ONLY)
	//@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private VerificationToken token;


}

