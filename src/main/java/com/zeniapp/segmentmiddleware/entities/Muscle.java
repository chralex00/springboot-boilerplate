package com.zeniapp.segmentmiddleware.entities;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.zeniapp.segmentmiddleware.enums.MuscleDimension;
import com.zeniapp.segmentmiddleware.enums.MuscleForm;
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
    private String id;
    
    @Getter
    @Setter
    @Indexed(unique = true)
    public String name;

    @Getter
    @Setter
    public String origin;

    @Getter
    @Setter
    public String insertion;

    @Getter
    @Setter
    private String functions;

    @Getter
    @Setter
    private MuscleForm form;

    @Getter
    @Setter
    private MuscleDimension dimension;

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
