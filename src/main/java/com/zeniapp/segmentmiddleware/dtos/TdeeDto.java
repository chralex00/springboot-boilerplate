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
public class TdeeDto {
    @Getter
    @Setter
	private String id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String performedOn;

    @Getter
    @Setter
    private Boolean isArchived;
    
    @Getter
    @Setter
    private String accountId;

    @Getter
    @Setter
    private List<String> tags;

    @Getter
    @Setter
    private String idealWeightFormula;
    
    @Getter
    @Setter
    private String basalMetabolismRateFormula;
    
    @Getter
    @Setter
    private String weightGoal;

    @Getter
    @Setter
    private TdeePersonalInfoDto personalInfo;

    @Getter
    @Setter
    private TdeeResultDto result;

    @Getter
    @Setter
    private String createdOn;

    @Getter
    @Setter
    private String updatedOn;
}
