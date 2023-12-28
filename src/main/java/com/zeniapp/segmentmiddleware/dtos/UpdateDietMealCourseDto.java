package com.zeniapp.segmentmiddleware.dtos;

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
public class UpdateDietMealCourseDto {
    @Getter
    @Setter
    @Size(
        min = Constants.DIET_MEAL_COURSE_NOTES_MIN_LENGTH,
        max = Constants.DIET_MEAL_COURSE_NOTES_MAX_LENGTH,
        message = "notes must have between " + Constants.DIET_MEAL_COURSE_NOTES_MIN_LENGTH + " and " + Constants.DIET_MEAL_COURSE_NOTES_MAX_LENGTH + " characters"
    )
    private String notes;
    
    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Float quantity;

    @Getter
    @Setter
    @NotNull(message = "quantityExpressedIn cannot be null")
    @NotBlank(message = "quantityExpressedIn cannot be an empty string")
    @Size(
        min = Constants.FOOD_QUANTITY_EXPRESSED_IN_MIN_LENGTH,
        max = Constants.FOOD_QUANTITY_EXPRESSED_IN_MAX_LENGTH,
        message = "quantityExpressedIn must have between " + Constants.FOOD_QUANTITY_EXPRESSED_IN_MIN_LENGTH + " and " + Constants.FOOD_QUANTITY_EXPRESSED_IN_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.FOOD_QUANTITY_EXPRESSED_IN_REGEXP, message = "quantityExpressedIn must respect to the following pattern: " + Constants.FOOD_QUANTITY_EXPRESSED_IN_REGEXP)
    private String quantityExpressedIn;
    
    @Getter
    @Setter
    @Valid
    @NotNull(message = "food cannot be null")
    private UpdateDietMealCourseFoodDto food;
}