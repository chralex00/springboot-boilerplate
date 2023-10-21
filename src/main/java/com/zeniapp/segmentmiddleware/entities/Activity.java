package com.zeniapp.segmentmiddleware.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.zeniapp.segmentmiddleware.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "activities")
public class Activity {
	@Id
    @Getter
    @Setter
	private String id;

    @Getter
    @Setter
    @Indexed(unique = true)
    private String name;

    @Getter
    @Setter
    private String description;
    
    @Getter
    @Setter
    private ActivityType type;

    @Getter
    @Setter
    private Integer mets;

    @Getter
    @Setter
    private List<String> tags;

    @DBRef
    @Getter
    @Setter
    private List<Exercise> mainExercises; // max 15 exercises

    @Getter
    @Setter
    private Boolean isPublished;

    @Getter
    @Setter
    private Boolean isDeleted;
}