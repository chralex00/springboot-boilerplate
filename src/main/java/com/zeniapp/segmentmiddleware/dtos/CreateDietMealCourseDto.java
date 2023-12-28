package com.zeniapp.segmentmiddleware.dtos;

import com.zeniapp.segmentmiddleware.constants.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class CreateDietMealCourseDto {
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
    @NotNull(message = "type cannot be null")
    @Size(
        min = Constants.DIET_MEAL_COURSE_TYPE_MIN_LENGTH,
        max = Constants.DIET_MEAL_COURSE_TYPE_MAX_LENGTH,
        message = "type must have between " + Constants.DIET_MEAL_COURSE_TYPE_MIN_LENGTH + " and " + Constants.DIET_MEAL_COURSE_TYPE_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.DIET_MEAL_COURSE_TYPE_REGEXP, message = "type must respect to the following pattern: " + Constants.DIET_MEAL_COURSE_TYPE_REGEXP)
    private String type;

    @Getter
    @Setter
    @NotNull(message = "quantity cannot be null")
    @Min(value = Constants.FOOD_QUANTITY_MIN_VALUE, message = "quantity must be greater than, or equal to " + Constants.FOOD_QUANTITY_MIN_VALUE)
    @Max(value = Constants.FOOD_QUANTITY_MAX_VALUE, message = "quantity must be lower than, or equal to " + Constants.FOOD_QUANTITY_MAX_VALUE)
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
    private CreateDietMealCourseFoodDto food;
}