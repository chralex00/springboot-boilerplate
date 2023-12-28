package com.zeniapp.segmentmiddleware.dtos;

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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateDietMealCourseFoodDto {
    @Getter
    @Setter
    @NotNull(message = "food name cannot be null")
    @NotBlank(message = "food name cannot be an empty string")
    @Size(
        min = Constants.FOOD_NAME_MIN_LENGTH,
        max = Constants.FOOD_NAME_MAX_LENGTH,
        message = "food name must have between " + Constants.FOOD_NAME_MIN_LENGTH + " and " + Constants.FOOD_NAME_MAX_LENGTH + " characters"
    )
    private String name;

    @Getter
    @Setter
    @NotNull(message = "food category cannot be null")
    @NotBlank(message = "food category cannot be an empty string")
    @Size(
        min = Constants.FOOD_CATEGORY_MIN_LENGTH,
        max = Constants.FOOD_CATEGORY_MAX_LENGTH,
        message = "food category must have between " + Constants.FOOD_CATEGORY_MIN_LENGTH + " and " + Constants.FOOD_CATEGORY_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.FOOD_CATEGORY_REGEXP, message = "food category must respect to the following pattern: " + Constants.FOOD_CATEGORY_REGEXP)
    private String category;

    @Getter
    @Setter
    @NotNull(message = "food energy cannot be null")
    @Min(value = Constants.MIN_ENERGY_VALUE, message = "food energy must be greater than, or equal to " + Constants.MIN_ENERGY_VALUE)
    @Max(value = Constants.MAX_ENERGY_VALUE, message = "food energy must be lower than, or equal to " + Constants.MAX_ENERGY_VALUE)
    private Integer energy;

    @Getter
    @Setter
    @NotNull(message = "food referencePortion cannot be null")
    @Min(value = Constants.MIN_REFERENCE_PORTION_QUANTITY_VALUE, message = "food referencePortion must be greater than, or equal to " + Constants.MIN_REFERENCE_PORTION_QUANTITY_VALUE)
    @Max(value = Constants.MAX_REFERENCE_PORTION_QUANTITY_VALUE, message = "food referencePortion must be lower than, or equal to " + Constants.MAX_REFERENCE_PORTION_QUANTITY_VALUE)
    private Integer referencePortion;

    @Getter
    @Setter
    @NotNull(message = "food proteins cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "food proteins must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "food proteins must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer proteins;

    @Getter
    @Setter
    @NotNull(message = "food simplexCarbs cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "food simplexCarbs must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "food simplexCarbs must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer simplexCarbs;

    @Getter
    @Setter
    @NotNull(message = "food complexCarbs cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "food complexCarbs must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "food complexCarbs must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer complexCarbs;

    @Getter
    @Setter
    @NotNull(message = "food saturatedFats cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "food saturatedFats must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "food saturatedFats must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer saturatedFats;

    @Getter
    @Setter
    @NotNull(message = "food unsaturatedFats cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "food unsaturatedFats must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "food unsaturatedFats must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer unsaturatedFats;

    @Getter
    @Setter
    @NotNull(message = "food fibers cannot be null")
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "food fibers must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "food fibers must be lower than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer fibers;

    @Getter
    @Setter
    @NotNull(message = "food cholesterol cannot be null")
    @Min(value = Constants.MIN_CHOLESTEROL_VALUE, message = "food cholesterol must be greater than, or equal to " + Constants.MIN_CHOLESTEROL_VALUE)
    @Max(value = Constants.MAX_CHOLESTEROL_VALUE, message = "food cholesterol must be lower than, or equal to " + Constants.MAX_CHOLESTEROL_VALUE)
    private Integer cholesterol;

    @Getter
    @Setter
    @NotNull(message = "food vitamins cannot be null")
    @Size(max = Constants.VITAMINS_MAX_LENGTH, message = "food vitamins must have max " + Constants.VITAMINS_MAX_LENGTH + " elements")
    private List<
        @Size(
            min = Constants.STRING_FILTER_MIN_LENGTH,
            max = Constants.STRING_FILTER_MAX_LENGTH,
            message = "food vitamin must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
        )
        @Pattern(regexp = Constants.VITAMIN_REGEXP, message = "food vitamin must respect to the following pattern: " + Constants.VITAMIN_REGEXP)
    String> vitamins;

    @Getter
    @Setter
    @NotNull(message = "food minerals cannot be null")
    @Size(max = Constants.MINERALS_MAX_LENGTH, message = "food minerals must have max " + Constants.MINERALS_MAX_LENGTH + " elements")
    private List<
        @Size(
            min = Constants.STRING_FILTER_MIN_LENGTH,
            max = Constants.STRING_FILTER_MAX_LENGTH,
            message = "food mineral must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
        )
        @Pattern(regexp = Constants.MINERAL_REGEXP, message = "food mineral must respect to the following pattern: " + Constants.MINERAL_REGEXP)
    String> minerals;
}