package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TdeePersonalInfoDto {
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
    private Integer age;

    @Getter
    @Setter
    private Float height;

    @Getter
    @Setter
    private Float actualWeight;

    @Getter
    @Setter
    private String activityLevel;

    @Getter
    @Setter
    private List<TdeeActivityDto> activities;
}