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
public class UpdateDietMealDto {
    @Getter
    @Setter
    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be an empty string")
    @Size(
        min = Constants.DIET_SESSION_TITLE_MIN_LENGTH,
        max = Constants.DIET_SESSION_TITLE_MAX_LENGTH,
        message = "title must have between " + Constants.DIET_SESSION_TITLE_MIN_LENGTH + " and " + Constants.DIET_SESSION_TITLE_MAX_LENGTH + " characters"
    )
    private String title;

    @Getter
    @Setter
    @NotNull(message = "meal type cannot be null")
    @NotBlank(message = "meal type cannot be an empty string")
    @Size(
        min = Constants.DIET_MEAL_TYPE_MIN_LENGTH,
        max = Constants.DIET_MEAL_TYPE_MAX_LENGTH,
        message = "meal type must have between " + Constants.DIET_MEAL_TYPE_MIN_LENGTH + " and " + Constants.DIET_MEAL_TYPE_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.DIET_MEAL_TYPE_REGEXP, message = "meal type must respect to the following pattern: " + Constants.DIET_MEAL_TYPE_REGEXP)
    private String type;

    @Getter
    @Setter
    @Valid
    @NotNull(message = "courses cannot be null")
    @Size(max = Constants.DIET_MEAL_COURSES_MAX_LENGTH, message = "courses must have max " + Constants.DIET_MEAL_COURSES_MAX_LENGTH + " elements")
    private List<UpdateDietMealCourseDto> courses;
}