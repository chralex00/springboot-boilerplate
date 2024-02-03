package com.zeniapp.segmentmiddleware.entities;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "muscles")
public class Muscle {
    @Id
    @Getter
    @Setter
    @NonNull
    private String id;
    
    @Getter
    @Setter
    @Indexed(unique = true)
    private String name;

    @Getter
    @Setter
    private String origin;

    @Getter
    @Setter
    private String insertion;

    @Getter
    @Setter
    private String functions;

    @Getter
    @Setter
    private String form;

    @Getter
    @Setter
    private String dimension;

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
