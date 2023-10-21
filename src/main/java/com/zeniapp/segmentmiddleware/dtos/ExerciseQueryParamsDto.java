package com.zeniapp.segmentmiddleware.dtos;

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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExerciseQueryParamsDto {
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
    @Pattern(regexp = Constants.EXERCISE_SORT_FIELDS_REGEXP, message = "sortField must respect to the following pattern: " + Constants.EXERCISE_SORT_FIELDS_REGEXP)
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
        message = "difficulty must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.EXERCISE_DIFFICULTY_REGEXP, message = "difficulty must respect to the following pattern: " + Constants.EXERCISE_DIFFICULTY_REGEXP)
    private String difficulty;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "category must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.EXERCISE_CATEGORY_REGEXP, message = "category must respect to the following pattern: " + Constants.EXERCISE_CATEGORY_REGEXP)
    private String category;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "type must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.EXERCISE_TYPE_REGEXP, message = "type must respect to the following pattern: " + Constants.EXERCISE_TYPE_REGEXP)
    private String type;

    @Getter
    @Setter
    // to do
    private List<String> agonistMuscles;

    @Getter
    @Setter
    // to do
    private List<String> antagonistMuscles;

    @Getter
    @Setter
    // to do
    private List<String> synergisticMuscles;

    @Getter
    @Setter
    // to do
    private List<String> fixatorMuscles;
}