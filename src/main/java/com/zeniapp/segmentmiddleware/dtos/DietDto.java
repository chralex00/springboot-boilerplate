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
public class DietDto {
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
    private List<String> tags;

    @Getter
    @Setter
    private String goal;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String startsOn;

    @Getter
    @Setter
    private Integer duration;

    @Getter
    @Setter
    private Boolean isArchived;

    @Getter
    @Setter
    private String createdOn;

    @Getter
    @Setter
    private String updatedOn;

    @Getter
    @Setter
    private String accountId;

    @Getter
    @Setter
    private List<DietActivityDto> dietForActivities;
    
    @Getter
    @Setter
    private List<DietMealDto> meals;
}
