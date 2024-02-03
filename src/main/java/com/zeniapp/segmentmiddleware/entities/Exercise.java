package com.zeniapp.segmentmiddleware.entities;

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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "exercises")
public class Exercise {
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
    private String execution;
  
    @Getter
    @Setter
    private String commonErrors;
  
    @Getter
    @Setter
    private String difficulty;
  
    @Getter
    @Setter
    private String category;
  
    @Getter
    @Setter
    private String type;
    
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