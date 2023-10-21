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
public class ActivityQueryParamsDto {
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
    @Pattern(regexp = Constants.ACTIVITY_SORT_FIELDS_REGEXP, message = "sortField must respect to the following pattern: " + Constants.ACTIVITY_SORT_FIELDS_REGEXP)
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
        message = "type must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ACTIVITY_TYPE_REGEXP, message = "type must respect to the following pattern: " + Constants.ACTIVITY_TYPE_REGEXP)
    private String type;

    @Getter
    @Setter
    @Size(max = Constants.TAGS_MAX_LENGTH, message = "tags must have max " + Constants.TAGS_MAX_LENGTH + " exercise ID")
    private List<
        @Size(
            min = Constants.STRING_FILTER_MIN_LENGTH,
            max = Constants.STRING_FILTER_MAX_LENGTH,
            message = "tags must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
        )
    String> tags;

    @Getter
    @Setter
    @Size(max = Constants.MAIN_EXERCISES_MAX_LENGTH, message = "mainExercises must have max " + Constants.MAIN_EXERCISES_MAX_LENGTH + " exercise ID")
    private List<
        @Size(
            min = Constants.STRING_FILTER_MIN_LENGTH,
            max = Constants.STRING_FILTER_MAX_LENGTH,
            message = "type must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
        )
    String> mainExercises;

    @Getter
    @Setter
    @Min(value = Constants.MIN_METS_VALUE, message = "metsMin must be greater than, or equal to " + Constants.MIN_METS_VALUE)
    @Max(value = Constants.MAX_METS_VALUE, message = "metsMin must be greater than, or equal to " + Constants.MAX_METS_VALUE)
    private Integer metsMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_METS_VALUE, message = "metsMax must be greater than, or equal to " + Constants.MIN_METS_VALUE)
    @Max(value = Constants.MAX_METS_VALUE, message = "metsMax must be greater than, or equal to " + Constants.MAX_METS_VALUE)
    private Integer metsMax;
}