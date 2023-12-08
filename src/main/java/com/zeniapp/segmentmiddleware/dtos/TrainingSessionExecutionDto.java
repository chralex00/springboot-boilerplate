package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.enums.ExerciseCategory;
import com.zeniapp.segmentmiddleware.enums.ExerciseDifficulty;
import com.zeniapp.segmentmiddleware.enums.ExerciseType;
import com.zeniapp.segmentmiddleware.enums.IntensityMeasureUnit;
import com.zeniapp.segmentmiddleware.enums.IntensityTechnique;
import com.zeniapp.segmentmiddleware.enums.SetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingSessionExecutionDto {
    @Getter
    @Setter
    private List<TrainingSessionExecutionSetDto> sets;

    @Getter
    @Setter
    private String notes;

    @Getter
    @Setter
    private TrainingSessionExecutionExerciseDto exercise;

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private class TrainingSessionExecutionSetDto {
        @Getter
        @Setter
        private String reps;
        
        @Getter
        @Setter
        private String intensity;

        @Getter
        @Setter
        private String timeUnderTension;

        @Getter
        @Setter
        private IntensityMeasureUnit intensityMeasureUnit;

        @Getter
        @Setter
        private IntensityTechnique intensityTechnique;
        
        @Getter
        @Setter
        private SetType type;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private class TrainingSessionExecutionExerciseDto {
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
}
