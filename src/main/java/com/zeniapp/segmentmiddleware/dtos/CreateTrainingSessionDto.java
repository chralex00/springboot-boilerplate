package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.constants.Constants;
import jakarta.validation.Valid;
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
public class CreateTrainingSessionDto {
    @Getter
    @Setter
    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be an empty string")
    @Size(
        min = Constants.TRAINING_SESSION_TITLE_MIN_LENGTH,
        max = Constants.TRAINING_SESSION_TITLE_MAX_LENGTH,
        message = "title must have between " + Constants.TRAINING_SESSION_TITLE_MIN_LENGTH + " and " + Constants.TRAINING_SESSION_TITLE_MAX_LENGTH + " characters"
    )
    private String title;

    @Getter
    @Setter
    @Size(
        min = Constants.ESTIMATED_STARTING_TIME_MIN_LENGTH,
        max = Constants.ESTIMATED_STARTING_TIME_MAX_LENGTH,
        message = "recommendedStartingTime must have between " + Constants.ESTIMATED_STARTING_TIME_MIN_LENGTH + " and " + Constants.ESTIMATED_STARTING_TIME_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ESTIMATED_STARTING_TIME_REGEXP, message = "recommendedStartingTime must respect to the following pattern: " + Constants.ESTIMATED_STARTING_TIME_REGEXP)
    private String recommendedStartingTime;

    @Getter
    @Setter
    @Size(
        min = Constants.ESTIMATED_DURATION_MIN_LENGTH,
        max = Constants.ESTIMATED_DURATION_MAX_LENGTH,
        message = "estimatedDuration must have between " + Constants.ESTIMATED_DURATION_MIN_LENGTH + " and " + Constants.ESTIMATED_DURATION_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ESTIMATED_DURATION_REGEXP, message = "estimatedDuration must respect to the following pattern: " + Constants.ESTIMATED_DURATION_REGEXP)
    private String estimatedDuration;

    @Getter
    @Setter
    @NotNull(message = "estimatedEffortLevel cannot be null")
    @NotBlank(message = "estimatedEffortLevel cannot be an empty string")
    @Size(
        min = Constants.TRAINING_TYPE_MIN_LENGTH,
        max = Constants.TRAINING_TYPE_MAX_LENGTH,
        message = "estimatedEffortLevel must have between " + Constants.ESTIMATED_EFFORT_LEVEL_MIN_LENGTH + " and " + Constants.ESTIMATED_EFFORT_LEVEL_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ESTIMATED_EFFORT_LEVEL_REGEXP, message = "estimatedEffortLevel must respect to the following pattern: " + Constants.ESTIMATED_EFFORT_LEVEL_REGEXP)
    private String estimatedEffortLevel;

    @Getter
    @Setter
    @Valid
    @NotNull(message = "executions cannot be null")
    @Size(max = Constants.TRAINING_SESSION_EXECUTIONS_MAX_LENGTH, message = "executions must have max " + Constants.TRAINING_SESSION_EXECUTIONS_MAX_LENGTH + " elements")
    private List<CreateTrainingSessionExecutionDto> executions;
}
