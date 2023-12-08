package com.zeniapp.segmentmiddleware.entities;

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
public class TrainingSessionExecution {
    @Getter
    @Setter
    private List<TrainingSessionExecutionSet> sets; // max 50 sets

    @Getter
    @Setter
    private String notes;

    @Getter
    @Setter
    private TrainingSessionExecutionExercise exercise;

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private class TrainingSessionExecutionSet {
        @Getter
        @Setter
        private String reps;
        
        @Getter
        @Setter
        private String intensity;

        @Getter
        @Setter
        private String timeUnderTension; // expressed in A-B-C-D format, where A, B, C and D are all a numbers between 0 and 20

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
    private class TrainingSessionExecutionExercise { // partial type of the Exercise entity
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
