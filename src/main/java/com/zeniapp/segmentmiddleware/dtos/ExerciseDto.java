package com.zeniapp.segmentmiddleware.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExerciseDto {
    @Getter
    @Setter
    private String id;
    
    @Getter
    @Setter
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
    
    @Getter
    @Setter
    private List<Object> agonistMuscles; // to do\

    @Getter
    @Setter
    private List<Object> antagonistMuscles; // to do
    
    @Getter
    @Setter
    private List<Object> synergisticMuscles; // to do
    
    @Getter
    @Setter
    private List<Object> fixatorMuscles; // to do
    
    @Getter
    @Setter
    private Boolean isPublished;
}