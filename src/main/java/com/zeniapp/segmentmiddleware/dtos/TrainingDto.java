package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.enums.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingDto {
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
    private TrainingType type;

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
    private String accountId;

    @Getter
    @Setter
    private List<TrainingActivityDto> trainingForActivities;

    @Getter
    @Setter
    private List<TrainingSessionDto> sessions;

    @Getter
    @Setter
    private String createdOn;

    @Getter
    @Setter
    private String updatedOn;
}