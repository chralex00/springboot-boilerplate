package com.zeniapp.segmentmiddleware.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.zeniapp.segmentmiddleware.enums.ExerciseCategory;
import com.zeniapp.segmentmiddleware.enums.ExerciseDifficulty;
import com.zeniapp.segmentmiddleware.enums.ExerciseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "exercises")
public class Exercise {
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
    private String execution;
  
    @Getter
    @Setter
    private String commonErrors;
  
    @Getter
    @Setter
    private ExerciseDifficulty difficulty;
  
    @Getter
    @Setter
    private ExerciseCategory category;
  
    @Getter
    @Setter
    private ExerciseType type;
    
    @DBRef
    @Getter
    @Setter
    private List<Muscle> agonistMuscles; // max 5 muscles

    @DBRef
    @Getter
    @Setter
    private List<Muscle> antagonistMuscles; // max 5 muscles
    
    @DBRef
    @Getter
    @Setter
    private List<Muscle> synergisticMuscles; // max 5 muscles
    
    @DBRef
    @Getter
    @Setter
    private List<Muscle> fixatorMuscles; // max 5 muscles
    
    @Getter
    @Setter
    private Boolean isPublished;

    @Getter
    @Setter
    private Boolean isDeleted;
}