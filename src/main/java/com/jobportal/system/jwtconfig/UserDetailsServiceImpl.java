package com.jobportal.system.jwtconfig;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobportal.system.entity.User;
import com.jobportal.system.exceptionhandler.RecordNotFoundException;
import com.jobportal.system.repository.UserRepository;

import lombok.extern.log4j.Log4j2;





@Service

@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
   private UserRepository ur;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = ur.findByUsername(username);

       
		
		try {
            return new UserDetailsImpl(user.orElseThrow(()->new RecordNotFoundException("User NOt Found, "+username)));
        } catch (RecordNotFoundException e) {
     
        }
        return null;
    }
}


