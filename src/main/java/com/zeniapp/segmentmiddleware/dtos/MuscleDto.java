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
public class MuscleDto {
    @Getter
    @Setter
    private String id;
    
    @Getter
    @Setter
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
    private String form;

    @Getter
    @Setter
    private String dimension;

    @Getter
    @Setter
    private List<ExerciseWithoutMusclesDto> mainExercises;
    
    @Getter
    @Setter
    private Boolean isPublished;
}