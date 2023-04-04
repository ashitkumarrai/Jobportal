package com.jobportal.system.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Component
@Builder
public class Certification {

    private String name;
    private String issuingOrganization;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Kolkata")
    private Date issueDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Kolkata")
    private Date expirationDate;
    private String credentialId;

}
