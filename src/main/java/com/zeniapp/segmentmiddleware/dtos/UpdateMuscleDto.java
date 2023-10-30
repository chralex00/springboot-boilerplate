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
public class UpdateMuscleDto {
    @Getter
    @Setter
    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be an empty string")
    @Size(
        min = Constants.MUSCLE_NAME_MIN_LENGTH,
        max = Constants.MUSCLE_NAME_MAX_LENGTH,
        message = "name must have between " + Constants.MUSCLE_NAME_MIN_LENGTH + " and " + Constants.MUSCLE_NAME_MAX_LENGTH + " characters"
    )
    private String name;

    @Getter
    @Setter
    @NotNull(message = "origin cannot be null")
    @NotBlank(message = "origin cannot be an empty string")
    @Size(
        min = Constants.MUSCLE_ORIGIN_MIN_LENGTH,
        max = Constants.MUSCLE_ORIGIN_MAX_LENGTH,
        message = "origin must have between " + Constants.MUSCLE_ORIGIN_MIN_LENGTH + " and " + Constants.MUSCLE_ORIGIN_MAX_LENGTH + " characters"
    )
    private String origin;

    @Getter
    @Setter
    @NotNull(message = "insertion cannot be null")
    @NotBlank(message = "insertion cannot be an empty string")
    @Size(
        min = Constants.MUSCLE_INSERTION_MIN_LENGTH,
        max = Constants.MUSCLE_INSERTION_MAX_LENGTH,
        message = "insertion must have between " + Constants.MUSCLE_INSERTION_MIN_LENGTH + " and " + Constants.MUSCLE_INSERTION_MAX_LENGTH + " characters"
    )
    private String insertion;

    @Getter
    @Setter
    @NotNull(message = "functions cannot be null")
    @NotBlank(message = "functions cannot be an empty string")
    @Size(
        min = Constants.MUSCLE_FUNCTIONS_MIN_LENGTH,
        max = Constants.MUSCLE_FUNCTIONS_MAX_LENGTH,
        message = "functions must have between " + Constants.MUSCLE_FUNCTIONS_MIN_LENGTH + " and " + Constants.MUSCLE_FUNCTIONS_MAX_LENGTH + " characters"
    )
    private String functions;

    @Getter
    @Setter
    @NotNull(message = "form cannot be null")
    @NotBlank(message = "form cannot be an empty string")
    @Size(
        min = Constants.MUSCLE_FORM_MIN_LENGTH,
        max = Constants.MUSCLE_FORM_MAX_LENGTH,
        message = "form must have between " + Constants.MUSCLE_FORM_MIN_LENGTH + " and " + Constants.MUSCLE_FORM_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.MUSCLE_FORM_REGEXP, message = "form must respect to the following pattern: " + Constants.MUSCLE_FORM_REGEXP)
    private String form;

    @Getter
    @Setter
    @NotNull(message = "dimension cannot be null")
    @NotBlank(message = "dimension cannot be an empty string")
    @Size(
        min = Constants.MUSCLE_DIMENSION_MIN_LENGTH,
        max = Constants.MUSCLE_DIMENSION_MAX_LENGTH,
        message = "dimension must have between " + Constants.MUSCLE_DIMENSION_MIN_LENGTH + " and " + Constants.MUSCLE_DIMENSION_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.MUSCLE_DIMENSION_REGEXP, message = "dimension must respect to the following pattern: " + Constants.MUSCLE_DIMENSION_REGEXP)
    private String dimension;

    @Getter
    @Setter
    @NotNull(message = "mainExercises cannot be null")
    @Size(max = Constants.MAIN_EXERCISES_MAX_LENGTH, message = "mainExercises must have max " + Constants.MAIN_EXERCISES_MAX_LENGTH + " exercise ID")
    private List<
        @Size(
            min = Constants.STRING_FILTER_MIN_LENGTH,
            max = Constants.STRING_FILTER_MAX_LENGTH,
            message = "exercise ID must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
        )
    String> mainExercises;

    @Getter
    @Setter
    @NotNull(message = "isPublished cannot be null")
    private Boolean isPublished;
}
