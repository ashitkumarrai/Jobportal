package com.jobportal.system.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsernameAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
     
    public UsernameAlreadyUsedException() {
        super("username name already used!");
    }
}

