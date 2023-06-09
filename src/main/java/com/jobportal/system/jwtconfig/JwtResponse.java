package com.jobportal.system.jwtconfig;


import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jobportal.system.entity.User;

import lombok.Data;
import lombok.AllArgsConstructor;



@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String token;

      @JsonIgnoreProperties(value = {"password"})
	private UserDetails user;


}
