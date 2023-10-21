package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.constants.Constants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class FoodQueryParamsDto {
    @Getter
    @Setter
    @Min(value = Constants.MIN_OFFSET_VALUE, message = "offset must be greater than, or equal to " + Constants.MIN_OFFSET_VALUE)
    @Max(value = Constants.MAX_OFFSET_VALUE, message = "offset must be lower than, or equal to " + Constants.MAX_OFFSET_VALUE)
    private Integer offset;

    @Getter
    @Setter
    @Min(value = Constants.MIN_LIMIT_VALUE, message = "limit must be greater than, or equal to " + Constants.MIN_LIMIT_VALUE)
    @Max(value = Constants.MAX_LIMIT_VALUE, message = "limit must be lower than, or equal to " + Constants.MAX_LIMIT_VALUE)
    private Integer limit;

    @Getter
    @Setter
    @Pattern(regexp = Constants.FOOD_SORT_FIELDS_REGEXP, message = "sortField must respect to the following pattern: " + Constants.FOOD_SORT_FIELDS_REGEXP)
    private String sortField;

    @Getter
    @Setter
    @Pattern(regexp = Constants.SQL_ORDER_BY_REGEXP, message = "sortDirection must respect to the following pattern: " + Constants.SQL_ORDER_BY_REGEXP)
    private String sortDirection;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "name must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    private String name;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "category must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.MUSCLE_DIMENSION_REGEXP, message = "category must respect to the following pattern: " + Constants.MUSCLE_DIMENSION_REGEXP)
    private String category;

    @Getter
    @Setter
    @Min(value = Constants.MIN_ENERGY_VALUE, message = "energyMin must be greater than, or equal to " + Constants.MIN_ENERGY_VALUE)
    @Max(value = Constants.MAX_ENERGY_VALUE, message = "energyMin must be greater than, or equal to " + Constants.MAX_ENERGY_VALUE)
    private Integer energyMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_ENERGY_VALUE, message = "energyMax must be greater than, or equal to " + Constants.MIN_ENERGY_VALUE)
    @Max(value = Constants.MAX_ENERGY_VALUE, message = "energyMax must be greater than, or equal to " + Constants.MAX_ENERGY_VALUE)
    private Integer energyMax;

    @Getter
    @Setter
    @Min(value = Constants.MIN_REFERENCE_PORTION_QUANTITY_VALUE, message = "referencePortionQuantityMin must be greater than, or equal to " + Constants.MIN_REFERENCE_PORTION_QUANTITY_VALUE)
    @Max(value = Constants.MAX_REFERENCE_PORTION_QUANTITY_VALUE, message = "referencePortionQuantityMin must be greater than, or equal to " + Constants.MAX_REFERENCE_PORTION_QUANTITY_VALUE)
    private Integer referencePortionQuantityMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_REFERENCE_PORTION_QUANTITY_VALUE, message = "referencePortionQuantityMax must be greater than, or equal to " + Constants.MIN_REFERENCE_PORTION_QUANTITY_VALUE)
    @Max(value = Constants.MAX_REFERENCE_PORTION_QUANTITY_VALUE, message = "referencePortionQuantityMax must be greater than, or equal to " + Constants.MAX_REFERENCE_PORTION_QUANTITY_VALUE)
    private Integer referencePortionQuantityMax;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer proteinsMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer proteinsMax;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer simplexCarbsMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer simplexCarbsMax;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer complexCarbsMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer complexCarbsMax;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer saturatedFatsMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer saturatedFatsMax;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer unsaturatedFatsMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer unsaturatedFatsMax;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer fibersMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_MACRONUTRIENT_VALUE)
    @Max(value = Constants.MAX_MACRONUTRIENT_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_MACRONUTRIENT_VALUE)
    private Integer fibersMax;

    @Getter
    @Setter
    @Min(value = Constants.MIN_CHOLESTEROL_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_CHOLESTEROL_VALUE)
    @Max(value = Constants.MAX_CHOLESTEROL_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_CHOLESTEROL_VALUE)
    private Integer cholesterolMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_CHOLESTEROL_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MIN_CHOLESTEROL_VALUE)
    @Max(value = Constants.MAX_CHOLESTEROL_VALUE, message = "proteinsMin must be greater than, or equal to " + Constants.MAX_CHOLESTEROL_VALUE)
    private Integer cholesterolMax;

    @Getter
    @Setter
    // to do
    private List<String> vitamins;

    @Getter
    @Setter
    // to do
    private List<String> minerals;
}