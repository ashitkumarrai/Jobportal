package com.jobportal.system.entity;


import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor

@Component
@Builder
@Data
public class WorkExperience {

    private String title;
    private String employementType;
    private String companyName;
    private String location;
    private boolean isCurrentlyWorking;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Kolkata")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Kolkata")
    private Date endDate;

    public void setEndDate(Date endDate) {
        
        if(this.isCurrentlyWorking)
            this.endDate = null;
        else
            this.endDate = endDate;
    }


}
