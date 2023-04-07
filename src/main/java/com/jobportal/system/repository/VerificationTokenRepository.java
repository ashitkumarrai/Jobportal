package com.jobportal.system.repository;

import java.util.Optional;

import com.jobportal.system.entity.User;

public interface VerificationTokenRepository {
    
    Optional<User> findByToken(String username);
}
