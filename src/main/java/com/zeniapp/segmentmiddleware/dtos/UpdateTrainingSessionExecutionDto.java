package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.constants.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateTrainingSessionExecutionDto {
    @Getter
    @Setter
    @Valid
    @NotNull(message = "sets cannot be null")
    @Size(max = Constants.TRAINING_SESSION_EXECUTIONS_SETS_MAX_LENGTH, message = "sets must have max " + Constants.TRAINING_SESSION_EXECUTIONS_SETS_MAX_LENGTH + " elements")
    private List<UpdateTrainingSessionExecutionSetDto> sets;

    @Getter
    @Setter
    @Size(
        min = Constants.TRAINING_SESSION_EXECUTION_NOTES_MIN_LENGTH,
        max = Constants.TRAINING_SESSION_EXECUTION_NOTES_MAX_LENGTH,
        message = "notes must have between " + Constants.TRAINING_SESSION_EXECUTION_NOTES_MIN_LENGTH + " and " + Constants.TRAINING_SESSION_EXECUTION_NOTES_MAX_LENGTH + " characters"
    )
    private String notes;

    @Getter
    @Setter
    @Valid
    @NotNull(message = "exercise cannot be null")
    private UpdateTrainingSessionExecutionExerciseDto exercise;
}
