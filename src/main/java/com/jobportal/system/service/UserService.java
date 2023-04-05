package com.jobportal.system.service;


import org.springframework.stereotype.Service;

import com.jobportal.system.entity.User;
@Service
public interface UserService {

    void changePassword(String currentPassword, String newPassword);

    User registerUser(@jakarta.validation.Valid User managedUserVM);

  
    
}
