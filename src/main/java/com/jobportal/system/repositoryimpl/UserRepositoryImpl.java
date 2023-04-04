package com.jobportal.system.repositoryimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jobportal.system.SystemApplication;
import com.jobportal.system.entity.User;
import com.jobportal.system.repository.UserRepository;

import lombok.extern.log4j.Log4j2;
@Repository
@Log4j2
public class UserRepositoryImpl implements UserRepository{

    @Override
    public Optional<User> findByUsername(String username) {






       return  SystemApplication.users.stream().filter((user) -> user.getUsername().equalsIgnoreCase(username)).findFirst();
    }

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Optional<User> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public boolean existsById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }
    
}
