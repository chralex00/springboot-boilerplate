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
public class TdeeQueryParamsDto {
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
    @Pattern(regexp = Constants.TDEE_SORT_FIELDS_REGEXP, message = "sortField must respect to the following pattern: " + Constants.TDEE_SORT_FIELDS_REGEXP)
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
    private Boolean isArchived;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "performedOn must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ISO8601_FORMAT_REGEXP, message = "performedOn must respect to the following pattern: " + Constants.ISO8601_FORMAT_REGEXP)
    private String performedOnMin;

    @Getter
    @Setter
    @Size(
        min = Constants.TDEE_PERFORMED_ON_MIN_LENGTH,
        max = Constants.TDEE_PERFORMED_ON_MAX_LENGTH,
        message = "performedOn must have between " + Constants.TDEE_PERFORMED_ON_MIN_LENGTH + " and " + Constants.TDEE_PERFORMED_ON_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ISO8601_FORMAT_REGEXP, message = "performedOn must respect to the following pattern: " + Constants.ISO8601_FORMAT_REGEXP)
    private String performedOnMax;

    @Getter
    @Setter
    @Size(
        min = Constants.CREATED_ON_MIN_LENGTH,
        max = Constants.CREATED_ON_MAX_LENGTH,
        message = "createdOnMin must have between " + Constants.CREATED_ON_MIN_LENGTH + " and " + Constants.CREATED_ON_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ISO8601_FORMAT_REGEXP, message = "createdOnMin must respect to the following pattern: " + Constants.ISO8601_FORMAT_REGEXP)
    private String createdOnMin;

    @Getter
    @Setter
    @Size(
        min = Constants.CREATED_ON_MIN_LENGTH,
        max = Constants.CREATED_ON_MAX_LENGTH,
        message = "createdOnMax must have between " + Constants.CREATED_ON_MIN_LENGTH + " and " + Constants.CREATED_ON_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ISO8601_FORMAT_REGEXP, message = "createdOnMax must respect to the following pattern: " + Constants.ISO8601_FORMAT_REGEXP)
    private String createdOnMax;

    @Getter
    @Setter
    @Size(
        min = Constants.UPDATED_ON_MIN_LENGTH,
        max = Constants.UPDATED_ON_MAX_LENGTH,
        message = "updatedOnMin must have between " + Constants.UPDATED_ON_MIN_LENGTH + " and " + Constants.UPDATED_ON_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ISO8601_FORMAT_REGEXP, message = "updatedOnMin must respect to the following pattern: " + Constants.ISO8601_FORMAT_REGEXP)
    private String updatedOnMin;

    @Getter
    @Setter
    @Size(
        min = Constants.UPDATED_ON_MIN_LENGTH,
        max = Constants.UPDATED_ON_MAX_LENGTH,
        message = "updatedOnMax must have between " + Constants.UPDATED_ON_MIN_LENGTH + " and " + Constants.UPDATED_ON_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ISO8601_FORMAT_REGEXP, message = "updatedOnMax must respect to the following pattern: " + Constants.ISO8601_FORMAT_REGEXP)
    private String updatedOnMax;

    @Getter
    @Setter
    @Size(
        min = Constants.ACCOUNT_ID_MIN_LENGTH,
        max = Constants.ACCOUNT_ID_MAX_LENGTH,
        message = "accountId must have between " + Constants.ACCOUNT_ID_MIN_LENGTH + " and " + Constants.ACCOUNT_ID_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.UUID_REGEXP, message = "accountId must respect to the following pattern: " + Constants.UUID_REGEXP)
    private String accountId;

    @Getter
    @Setter
    @Size(max = Constants.TAGS_MAX_LENGTH, message = "tags must have max " + Constants.TAGS_MAX_LENGTH + " elements")
    private List<
        @Size(
            min = Constants.STRING_FILTER_MIN_LENGTH,
            max = Constants.STRING_FILTER_MAX_LENGTH,
            message = "tag must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
        )
    String> tags;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "idealWeightFormula must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    private String idealWeightFormula;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "basalMetabolismRateFormula must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    private String basalMetabolismRateFormula;
}