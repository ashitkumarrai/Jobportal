package com.jobportal.system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jobportal.system.entity.User;
@Repository
public interface UserRepository {

    Optional<User> findByUsername(String username);

    List<User> findAll();

    Optional<User> findById(Long id);

    boolean existsById(Long id);

    Optional<User> findByTokenToken(String token);
    
}
