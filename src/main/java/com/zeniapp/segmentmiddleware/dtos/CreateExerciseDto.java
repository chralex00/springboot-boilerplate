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
public class CreateExerciseDto {
    @Getter
    @Setter
    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be an empty string")
    @Size(
        min = Constants.ACTIVITY_NAME_MIN_LENGTH,
        max = Constants.ACTIVITY_NAME_MAX_LENGTH,
        message = "name must have between " + Constants.ACTIVITY_NAME_MIN_LENGTH + " and " + Constants.ACTIVITY_NAME_MAX_LENGTH + " characters"
    )
    private String name;
  
    @Getter
    @Setter
    @NotNull(message = "execution cannot be null")
    @NotBlank(message = "execution cannot be an empty string")
    @Size(
        min = Constants.EXERCISE_EXECUTION_MIN_LENGTH,
        max = Constants.EXERCISE_EXECUTION_MAX_LENGTH,
        message = "execution must have between " + Constants.EXERCISE_EXECUTION_MIN_LENGTH + " and " + Constants.EXERCISE_EXECUTION_MAX_LENGTH + " characters"
    )
    private String execution;
  
    @Getter
    @Setter
    @NotNull(message = "commonErrors cannot be null")
    @NotBlank(message = "commonErrors cannot be an empty string")
    @Size(
        min = Constants.EXERCISE_EXECUTION_MIN_LENGTH,
        max = Constants.EXERCISE_EXECUTION_MAX_LENGTH,
        message = "commonErrors must have between " + Constants.EXERCISE_EXECUTION_MIN_LENGTH + " and " + Constants.EXERCISE_EXECUTION_MAX_LENGTH + " characters"
    )
    private String commonErrors;
  
    @Getter
    @Setter
    @NotNull(message = "difficulty cannot be null")
    @NotBlank(message = "difficulty cannot be an empty string")
    @Size(
        min = Constants.EXERCISE_DIFFICULTY_MIN_LENGTH,
        max = Constants.EXERCISE_DIFFICULTY_MAX_LENGTH,
        message = "difficulty must have between " + Constants.EXERCISE_DIFFICULTY_MIN_LENGTH + " and " + Constants.EXERCISE_DIFFICULTY_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.EXERCISE_DIFFICULTY_REGEXP, message = "difficulty must respect to the following pattern: " + Constants.EXERCISE_DIFFICULTY_REGEXP)
    private String difficulty;
  
    @Getter
    @Setter
    @NotNull(message = "category cannot be null")
    @NotBlank(message = "category cannot be an empty string")
    @Size(
        min = Constants.EXERCISE_CATEGORY_MIN_LENGTH,
        max = Constants.EXERCISE_CATEGORY_MAX_LENGTH,
        message = "category must have between " + Constants.EXERCISE_CATEGORY_MIN_LENGTH + " and " + Constants.EXERCISE_CATEGORY_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.EXERCISE_CATEGORY_REGEXP, message = "category must respect to the following pattern: " + Constants.EXERCISE_CATEGORY_REGEXP)
    private String category;
  
    @Getter
    @Setter
    @NotNull(message = "type cannot be null")
    @NotBlank(message = "type cannot be an empty string")
    @Size(
        min = Constants.EXERCISE_TYPE_MIN_LENGTH,
        max = Constants.EXERCISE_TYPE_MAX_LENGTH,
        message = "type must have between " + Constants.EXERCISE_TYPE_MIN_LENGTH + " and " + Constants.EXERCISE_TYPE_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.EXERCISE_TYPE_REGEXP, message = "type must respect to the following pattern: " + Constants.EXERCISE_TYPE_REGEXP)
    private String type;
    
    @Getter
    @Setter
    // to do
    private List<String> agonistMuscles; // max 5 muscles

    @Getter
    @Setter
    // to do
    private List<String> antagonistMuscles; // max 5 muscles
    
    @Getter
    @Setter
    // to do
    private List<String> synergisticMuscles; // max 5 muscles
    
    @Getter
    @Setter
    // to do
    private List<String> fixatorMuscles; // max 5 muscles
    
    @Getter
    @Setter
    @NotNull(message = "isPublished cannot be null")
    private Boolean isPublished;

    @Getter
    @Setter
    @NotNull(message = "isDeleted cannot be null")
    private Boolean isDeleted;
}
