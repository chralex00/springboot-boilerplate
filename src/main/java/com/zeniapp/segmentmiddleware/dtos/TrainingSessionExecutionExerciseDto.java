package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.enums.ExerciseCategory;
import com.zeniapp.segmentmiddleware.enums.ExerciseDifficulty;
import com.zeniapp.segmentmiddleware.enums.ExerciseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingSessionExecutionExerciseDto {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private ExerciseDifficulty difficulty;

    @Getter
    @Setter
    private ExerciseCategory category;

    @Getter
    @Setter
    private ExerciseType type;
    
    @Getter
    @Setter
    private List<String> agonistMuscleNames;

    @Getter
    @Setter
    private List<String> antagonistMuscleNames;
    
    @Getter
    @Setter
    private List<String> synergisticMuscleNames;
    
    @Getter
    @Setter
    private List<String> fixatorMuscleNames;
}