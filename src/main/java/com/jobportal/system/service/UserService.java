package com.jobportal.system.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.jobportal.system.entity.User;
@Service
public interface UserService {

    void changePassword(String currentPassword, String newPassword);

    User registerUser(@Valid User managedUserVM);

  
    
}
