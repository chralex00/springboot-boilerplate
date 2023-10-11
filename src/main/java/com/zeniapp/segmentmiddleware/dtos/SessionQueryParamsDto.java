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
public class SessionQueryParamsDto {
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
    @Pattern(regexp = Constants.SESSION_SORT_FIELDS_REGEXP, message = "sortField must respect to the following pattern: " + Constants.SESSION_SORT_FIELDS_REGEXP)
    private String sortField;

    @Getter
    @Setter
    @Pattern(regexp = Constants.SQL_ORDER_BY_REGEXP, message = "sortDirection must respect to the following pattern: " + Constants.SQL_ORDER_BY_REGEXP)
    private String sortDirection;

    @Getter
    @Setter
    @Min(value = Constants.MIN_API_COUNTER_VALUE, message = "apiCounterMin must be greater than, or equal to " + Constants.MIN_API_COUNTER_VALUE)
    @Max(value = Constants.MAX_API_COUNTER_VALUE, message = "apiCounterMin must be lower than, or equal to " + Constants.MAX_API_COUNTER_VALUE)
    private Integer apiCounterMin;

    @Getter
    @Setter
    @Min(value = Constants.MIN_API_COUNTER_VALUE, message = "apiCounterMax must be greater than, or equal to " + Constants.MIN_API_COUNTER_VALUE)
    @Max(value = Constants.MAX_API_COUNTER_VALUE, message = "apiCounterMax must be lower than, or equal to " + Constants.MAX_API_COUNTER_VALUE)
    private Integer apiCounterMax;
    
    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "email must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    private String email;

    @Getter
    @Setter
    @Size(
        min = Constants.STRING_FILTER_MIN_LENGTH,
        max = Constants.STRING_FILTER_MAX_LENGTH,
        message = "username must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
    )
    private String username;

    @Getter
    @Setter
    @Min(value = 0, message = "createdOnMin must be greater than, or equal to " + Constants.MIN_TIMESTAMP_VALUE)
    @Max(value = Long.MAX_VALUE, message = "createdOnMin must be greater than, or equal to " + Constants.MAX_TIMESTAMP_VALUE)
    private Long createdOnMin;

    @Getter
    @Setter
    @Min(value = 0, message = "createdOnMax must be greater than, or equal to " + Constants.MIN_TIMESTAMP_VALUE)
    @Max(value = Long.MAX_VALUE, message = "createdOnMax must be greater than, or equal to " + Constants.MAX_TIMESTAMP_VALUE)
    private Long createdOnMax;

    @Getter
    @Setter
    @Min(value = 0, message = "lastActivityOnMin must be greater than, or equal to " + Constants.MIN_TIMESTAMP_VALUE)
    @Max(value = Long.MAX_VALUE, message = "lastActivityOnMin must be greater than, or equal to " + Constants.MAX_TIMESTAMP_VALUE)
    private Long lastActivityOnMin;

    @Getter
    @Setter
    @Min(value = 0, message = "lastActivityOnMax must be greater than, or equal to " + Constants.MIN_TIMESTAMP_VALUE)
    @Max(value = Long.MAX_VALUE, message = "lastActivityOnMax must be greater than, or equal to " + Constants.MAX_TIMESTAMP_VALUE)
    private Long lastActivityOnMax;
}
