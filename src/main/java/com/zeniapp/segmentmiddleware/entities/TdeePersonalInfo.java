package com.zeniapp.segmentmiddleware.entities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TdeePersonalInfo {
    @Getter
    @Setter
    private String firstName;
    
    @Getter
    @Setter
    private String lastName;
    
    @Getter
    @Setter
    private String biologicalGender;
    
    @Getter
    @Setter
    private Integer age; // between 5 and 140 years

    @Getter
    @Setter
    private Float height; // expressed in cm

    @Getter
    @Setter
    private Float actualWeight; // expressed in kg

    @Getter
    @Setter
    private String activityLevel;

    @Getter
    @Setter
    private List<TdeeActivity> activities;
}