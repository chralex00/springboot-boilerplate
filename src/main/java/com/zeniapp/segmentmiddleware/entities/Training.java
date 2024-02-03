package com.zeniapp.segmentmiddleware.entities;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "trainings")
public class Training {
    @Id
    @Getter
    @Setter
    @Nullable
	private String id;

    @Getter
    @Setter
    @Indexed
    private String title;

    @Getter
    @Setter
    private String description;
    
    @Getter
    @Setter
    private String goal;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String startsOn; // ISO date string

    @Getter
    @Setter
    private Integer duration; // expressed in weeks

    @Getter
    @Setter
    private Boolean isArchived;
    
    @Getter
    @Setter
    private String accountId;

    @Getter
    @Setter
    private List<String> tags; // max 15 tags

    @Getter
    @Setter
    private List<TrainingActivity> trainingForActivities;

    @Getter
    @Setter
    private List<TrainingSession> sessions;

    @Getter
    @Setter
    private String createdOn; // ISO date string

    @Getter
    @Setter
    private String updatedOn; // ISO date string
}
