package com.jobportal.system.entity;


import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Role {

    private Long id;
     
    private String name;
  
    
  
}

