package com.jobportal.system.entity;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


@JsonIgnoreProperties({ "media" })
public class File {
 
    
    private String id;
  

    


   
    private byte[] media;

   
    private String mediaContentType;



}
