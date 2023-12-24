package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.constants.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateTrainingSessionExecutionExerciseDto {
    @Getter
    @Setter
    @NotNull(message = "exercise name cannot be null")
    @NotBlank(message = "exercise name cannot be an empty string")
    @Size(
        min = Constants.ACTIVITY_NAME_MIN_LENGTH,
        max = Constants.ACTIVITY_NAME_MAX_LENGTH,
        message = "exercise name must have between " + Constants.ACTIVITY_NAME_MIN_LENGTH + " and " + Constants.ACTIVITY_NAME_MAX_LENGTH + " characters"
    )
    private String name;

    @Getter
    @Setter
    @NotNull(message = "exercise difficulty cannot be null")
    @NotBlank(message = "exercise difficulty cannot be an empty string")
    @Size(
        min = Constants.EXERCISE_DIFFICULTY_MIN_LENGTH,
        max = Constants.EXERCISE_DIFFICULTY_MAX_LENGTH,
        message = "exercise difficulty must have between " + Constants.EXERCISE_DIFFICULTY_MIN_LENGTH + " and " + Constants.EXERCISE_DIFFICULTY_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.EXERCISE_DIFFICULTY_REGEXP, message = "exercise difficulty must respect to the following pattern: " + Constants.EXERCISE_DIFFICULTY_REGEXP)
    private String difficulty;

    @Getter
    @Setter
    @NotNull(message = "exercise category cannot be null")
    @NotBlank(message = "exercise category cannot be an empty string")
    @Size(
        min = Constants.EXERCISE_CATEGORY_MIN_LENGTH,
        max = Constants.EXERCISE_CATEGORY_MAX_LENGTH,
        message = "exercise category must have between " + Constants.EXERCISE_CATEGORY_MIN_LENGTH + " and " + Constants.EXERCISE_CATEGORY_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.EXERCISE_CATEGORY_REGEXP, message = "exercise category must respect to the following pattern: " + Constants.EXERCISE_CATEGORY_REGEXP)    
    private String category;

    @Getter
    @Setter
    @NotNull(message = "exercise type cannot be null")
    @NotBlank(message = "exercise type cannot be an empty string")
    @Size(
        min = Constants.EXERCISE_TYPE_MIN_LENGTH,
        max = Constants.EXERCISE_TYPE_MAX_LENGTH,
        message = "exercise type must have between " + Constants.EXERCISE_TYPE_MIN_LENGTH + " and " + Constants.EXERCISE_TYPE_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.EXERCISE_TYPE_REGEXP, message = "exercise type must respect to the following pattern: " + Constants.EXERCISE_TYPE_REGEXP)    
    private String type;
    
    @Getter
    @Setter
    @NotNull(message = "exercise agonistMuscleNames cannot be null")
    @Size(
        max = Constants.AGONIST_MUSCLES_MAX_LENGTH,
        message = "exercise agonistMuscleNames must have max " + Constants.AGONIST_MUSCLES_MAX_LENGTH + " names"
    )
    private List<
        @NotNull(message = "agonist muscle name cannot be null")
        @NotBlank(message = "agonist muscle name cannot be an empty string")
        @Size(
            min = Constants.MUSCLE_NAME_MIN_LENGTH,
            max = Constants.MUSCLE_NAME_MAX_LENGTH,
            message = "agonist muscle name must have between " + Constants.MUSCLE_NAME_MIN_LENGTH + " and " + Constants.MUSCLE_NAME_MAX_LENGTH + " characters"
        )
    String> agonistMuscleNames;

    @Getter
    @Setter
    @NotNull(message = "exercise antagonistMuscleNames cannot be null")
    @Size(
        max = Constants.ANTAGONIST_MUSCLES_MAX_LENGTH,
        message = "exercise antagonistMuscleNames must have max " + Constants.ANTAGONIST_MUSCLES_MAX_LENGTH + " names"
    )
    private List<
        @NotNull(message = "antagonist muscle name cannot be null")
        @NotBlank(message = "antagonist muscle name cannot be an empty string")
        @Size(
            min = Constants.MUSCLE_NAME_MIN_LENGTH,
            max = Constants.MUSCLE_NAME_MAX_LENGTH,
            message = "antagonist muscle name must have between " + Constants.MUSCLE_NAME_MIN_LENGTH + " and " + Constants.MUSCLE_NAME_MAX_LENGTH + " characters"
        )     
    String> antagonistMuscleNames;
    
    @Getter
    @Setter
    @NotNull(message = "exercise synergisticMuscleNames cannot be null")
    @Size(
        max = Constants.SYNERGISTIC_MUSCLES_MAX_LENGTH,
        message = "exercise synergisticMuscleNames must have max " + Constants.SYNERGISTIC_MUSCLES_MAX_LENGTH + " names"
    )
    private List<
        @NotNull(message = "synergistic muscle name cannot be null")
        @NotBlank(message = "synergistic muscle name cannot be an empty string")
        @Size(
            min = Constants.MUSCLE_NAME_MIN_LENGTH,
            max = Constants.MUSCLE_NAME_MAX_LENGTH,
            message = "synergistic muscle name must have between " + Constants.MUSCLE_NAME_MIN_LENGTH + " and " + Constants.MUSCLE_NAME_MAX_LENGTH + " characters"
        )
    String> synergisticMuscleNames;
    
    @Getter
    @Setter
    @NotNull(message = "exercise fixatorMuscleNames cannot be null")
    @Size(
        max = Constants.FIXATOR_MUSCLES_MAX_LENGTH,
        message = "exercise fixatorMuscleNames must have max " + Constants.FIXATOR_MUSCLES_MAX_LENGTH + " names"
    )
    private List<
        @NotNull(message = "fixator muscle name cannot be null")
        @NotBlank(message = "fixator muscle name cannot be an empty string")
        @Size(
            min = Constants.MUSCLE_NAME_MIN_LENGTH,
            max = Constants.MUSCLE_NAME_MAX_LENGTH,
            message = "fixator muscle name must have between " + Constants.MUSCLE_NAME_MIN_LENGTH + " and " + Constants.MUSCLE_NAME_MAX_LENGTH + " characters"
        )
    String> fixatorMuscleNames;
}