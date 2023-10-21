package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.constants.Constants;
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
public class CreateFoodDto {
    @Getter
    @Setter
    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be an empty string")
    @Size(
        min = Constants.FOOD_NAME_MIN_LENGTH,
        max = Constants.FOOD_NAME_MAX_LENGTH,
        message = "name must have between " + Constants.FOOD_NAME_MIN_LENGTH + " and " + Constants.FOOD_NAME_MAX_LENGTH + " characters"
    )
    private String name;

    @Getter
    @Setter
    @NotNull(message = "category cannot be null")
    @NotBlank(message = "category cannot be an empty string")
    @Size(
        min = Constants.FOOD_CATEGORY_MIN_LENGTH,
        max = Constants.FOOD_CATEGORY_MAX_LENGTH,
        message = "category must have between " + Constants.FOOD_CATEGORY_MIN_LENGTH + " and " + Constants.FOOD_CATEGORY_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.FOOD_CATEGORY_REGEXP, message = "category must respect to the following pattern: " + Constants.FOOD_CATEGORY_REGEXP)
    private String category;

    @Getter
    @Setter
    @NotNull(message = "energy cannot be null")
    @Min(value = Constants.MIN_ENERGY_VALUE, message = "energy must be greater than, or equal to " + Constants.MIN_ENERGY_VALUE)
    @Max(value = Constants.MAX_ENERGY_VALUE, message = "energy must be lower than, or equal to " + Constants.MAX_ENERGY_VALUE)
    private Integer energy; // expressed in kcal/referencePortion

    @Getter
    @Setter
    @NotNull(message = "referencePortion cannot be null")
    @Min(value = Constants.MIN_REFERENCE_PORTION_QUANTITY_VALUE, message = "referencePortion must be greater than, or equal to " + Constants.MIN_REFERENCE_PORTION_QUANTITY_VALUE)
    @Max(value = Constants.MAX_REFERENCE_PORTION_QUANTITY_VALUE, message = "referencePortion must be lower than, or equal to " + Constants.MAX_REFERENCE_PORTION_QUANTITY_VALUE)
    private Integer referencePortion; // expressed in grams

    @Getter
    @Setter
    @NotNull(message = "proteins cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteins must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteins must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer proteins; // expressed in grams/referencePortion

    @Getter
    @Setter
    @NotNull(message = "simplexCarbs cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "simplexCarbs must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "simplexCarbs must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer simplexCarbs; // expressed in grams/referencePortion

    @Getter
    @Setter
    @NotNull(message = "complexCarbs cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "complexCarbs must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "complexCarbs must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer complexCarbs; // expressed in grams/referencePortion

    @Getter
    @Setter
    @NotNull(message = "saturatedFats cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "saturatedFats must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "saturatedFats must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer saturatedFats; // expressed in grams/referencePortion

    @Getter
    @Setter
    @NotNull(message = "unsaturatedFats cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "unsaturatedFats must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "unsaturatedFats must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer unsaturatedFats; // expressed in grams/referencePortion

    @Getter
    @Setter
    @NotNull(message = "fibers cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "fibers must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "fibers must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer fibers; // expressed in grams/referencePortion

    @Getter
    @Setter
    @NotNull(message = "cholesterol cannot be null")
    @Min(value = Constants.MIN_CHOLESTEROL_VALUE, message = "cholesterol must be greater than, or equal to " + Constants.MIN_CHOLESTEROL_VALUE)
    @Max(value = Constants.MAX_CHOLESTEROL_VALUE, message = "cholesterol must be lower than, or equal to " + Constants.MAX_CHOLESTEROL_VALUE)
    private Integer cholesterol; // expressed in milligrams/referencePortion

    @Getter
    @Setter
    // to do
    private List<String> vitamins; // top 5 vitamins most present

    @Getter
    @Setter
    // to do
    private List<String> minerals; // top 5 minerals most present
    
    @Getter
    @Setter
    @NotNull(message = "isPublished cannot be null")
    private Boolean isPublished;

    @Getter
    @Setter
    @NotNull(message = "isDeleted cannot be null")
    private Boolean isDeleted;
}
