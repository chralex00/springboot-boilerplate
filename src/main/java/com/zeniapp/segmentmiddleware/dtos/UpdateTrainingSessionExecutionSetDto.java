package com.zeniapp.segmentmiddleware.dtos;

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
public class UpdateTrainingSessionExecutionSetDto {
    @Getter
    @Setter
    @NotNull(message = "reps cannot be null")
    @NotBlank(message = "reps cannot be an empty string")
    @Size(
        min = Constants.REPS_MIN_LENGTH,
        max = Constants.REPS_MAX_LENGTH,
        message = "reps must have between " + Constants.REPS_MIN_LENGTH + " and " + Constants.REPS_MAX_LENGTH + " characters"
    )
    private String reps;
    
    @Getter
    @Setter
    @NotNull(message = "intensity cannot be null")
    @NotBlank(message = "intensity cannot be an empty string")
    @Size(
        min = Constants.INTENSITY_MIN_LENGTH,
        max = Constants.INTENSITY_MAX_LENGTH,
        message = "intensity must have between " + Constants.INTENSITY_MIN_LENGTH + " and " + Constants.INTENSITY_MAX_LENGTH + " characters"
    )
    private String intensity;

    @Getter
    @Setter
    @NotNull(message = "timeUnderTension cannot be null")
    @NotBlank(message = "timeUnderTension cannot be an empty string")
    @Size(
        min = Constants.TIME_UNDER_TENSION_MIN_LENGTH,
        max = Constants.TIME_UNDER_TENSION_MAX_LENGTH,
        message = "timeUnderTension must have between " + Constants.TIME_UNDER_TENSION_MIN_LENGTH + " and " + Constants.TIME_UNDER_TENSION_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.TIME_UNDER_TENSION_REGEXP, message = "timeUnderTension must respect to the following pattern: " + Constants.TIME_UNDER_TENSION_REGEXP)
    private String timeUnderTension;

    @Getter
    @Setter
    @NotNull(message = "intensityMeasureUnit cannot be null")
    @NotBlank(message = "intensityMeasureUnit cannot be an empty string")
    @Size(
        min = Constants.INTENSITY_MEASURE_UNIT_MIN_LENGTH,
        max = Constants.INTENSITY_MEASURE_UNIT_MAX_LENGTH,
        message = "intensityMeasureUnit must have between " + Constants.INTENSITY_MEASURE_UNIT_MIN_LENGTH + " and " + Constants.INTENSITY_MEASURE_UNIT_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.INTENSITY_MEASURE_UNIT_REGEXP, message = "intensityMeasureUnit must respect to the following pattern: " + Constants.TIME_UNDER_TENSION_REGEXP)
    private String intensityMeasureUnit;

    @Getter

    @Setter
    @NotNull(message = "intensityTechnique cannot be null")
    @NotBlank(message = "intensityTechnique cannot be an empty string")
    @Size(
        min = Constants.INTENSITY_TECHNIQUE_MIN_LENGTH,
        max = Constants.INTENSITY_TECHNIQUE_MAX_LENGTH,
        message = "intensityTechnique must have between " + Constants.INTENSITY_TECHNIQUE_MIN_LENGTH + " and " + Constants.INTENSITY_TECHNIQUE_MAX_LENGTH + " characters"
    )
    private String intensityTechnique;

    @Getter
    @Setter
    @NotNull(message = "type cannot be null")
    @NotBlank(message = "type cannot be an empty string")
    @Size(
        min = Constants.TRAINING_EXECUTION_SET_TYPE_MIN_LENGTH,
        max = Constants.TRAINING_EXECUTION_SET_TYPE_MAX_LENGTH,
        message = "type must have between " + Constants.TRAINING_EXECUTION_SET_TYPE_MIN_LENGTH + " and " + Constants.TRAINING_EXECUTION_SET_TYPE_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.SET_TYPE_REGEXP, message = "type must respect to the following pattern: " + Constants.SET_TYPE_REGEXP)
    private String type;
}