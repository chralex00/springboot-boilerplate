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

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingQueryParamsDto {
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
    @Pattern(regexp = Constants.TRAINING_SORT_FIELDS_REGEXP, message = "sortField must respect to the following pattern: " + Constants.TRAINING_SORT_FIELDS_REGEXP)
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
        message = "title must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    private String title;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "type must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.TRAINING_TYPE_REGEXP, message = "type must respect to the following pattern: " + Constants.TRAINING_TYPE_REGEXP)
    private String type;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "startsOnMin must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    // to do
    private String startsOnMin;

    @Getter
    @Setter
    @Size(
        min = Constants.TRAINING_STARTS_ON_MIN_LENGTH,
        max = Constants.TRAINING_STARTS_ON_MAX_LENGTH,
        message = "startsOnMax must have between " + Constants.TRAINING_STARTS_ON_MIN_LENGTH + " and " + Constants.TRAINING_STARTS_ON_MAX_LENGTH + " characters"
    )
    // to do
    private String startsOnMax;

    @Getter
    @Setter
    @Min(value = Constants.MIN_TRAINING_DURATION_VALUE, message = "durationMin must be greater than, or equal to " + Constants.MIN_TRAINING_DURATION_VALUE)
    @Max(value = Constants.MAX_TRAINING_DURATION_VALUE, message = "durationMin must be greater than, or equal to " + Constants.MAX_TRAINING_DURATION_VALUE)
    private Integer durationMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_TRAINING_DURATION_VALUE, message = "durationMax must be greater than, or equal to " + Constants.MIN_TRAINING_DURATION_VALUE)
    @Max(value = Constants.MAX_TRAINING_DURATION_VALUE, message = "durationMax must be greater than, or equal to " + Constants.MAX_TRAINING_DURATION_VALUE)
    private Integer durationMax;

    @Getter
    @Setter
    private Boolean isArchived;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "createdOnMin must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    // to do
    private String createdOnMin;

    @Getter
    @Setter
    @Size(
        min = Constants.TRAINING_STARTS_ON_MIN_LENGTH,
        max = Constants.TRAINING_STARTS_ON_MAX_LENGTH,
        message = "createdOnMax must have between " + Constants.TRAINING_STARTS_ON_MIN_LENGTH + " and " + Constants.TRAINING_STARTS_ON_MAX_LENGTH + " characters"
    )
    // to do
    private String createdOnMax;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "updatedOnMin must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    // to do
    private String updatedOnMin;

    @Getter
    @Setter
    @Size(
        min = Constants.TRAINING_STARTS_ON_MIN_LENGTH,
        max = Constants.TRAINING_STARTS_ON_MAX_LENGTH,
        message = "updatedOnMax must have between " + Constants.TRAINING_STARTS_ON_MIN_LENGTH + " and " + Constants.TRAINING_STARTS_ON_MAX_LENGTH + " characters"
    )
    // to do
    private String updatedOnMax;

    @Getter
    @Setter
    @Size(
        min = Constants.ACCOUNT_ID_MIN_LENGTH,
        max = Constants.ACCOUNT_ID_MAX_LENGTH,
        message = "accountId must have between " + Constants.ACCOUNT_ID_MIN_LENGTH + " and " + Constants.ACCOUNT_ID_MAX_LENGTH + " characters"
    )
    // to do
    private String accountId;
}