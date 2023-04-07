package com.jobportal.system.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;


 


    public VerificationToken(String token) {
        this.token = token;    
    }

  
       
    }

   


