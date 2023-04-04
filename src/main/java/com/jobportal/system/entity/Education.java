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
public class Education {

    private String school;
    private String degree;
    private String fieldOfStudy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Kolkata")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Kolkata")
    private Date endDate;
    private String grade;
    private String description;

}
