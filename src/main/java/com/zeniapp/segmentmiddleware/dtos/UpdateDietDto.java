package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
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
public class UpdateDietDto {
    @Getter
    @Setter
    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be an empty string")
    @Size(
        min = Constants.DIET_TITLE_MIN_LENGTH,
        max = Constants.DIET_TITLE_MAX_LENGTH,
        message = "title must have between " + Constants.DIET_TITLE_MIN_LENGTH + " and " + Constants.DIET_TITLE_MAX_LENGTH + " characters"
    )
    private String title;

    @Getter
    @Setter
    @Size(
        min = Constants.DIET_DESCRIPTION_MIN_LENGTH,
        max = Constants.DIET_DESCRIPTION_MAX_LENGTH,
        message = "description must have between " + Constants.DIET_DESCRIPTION_MIN_LENGTH + " and " + Constants.DIET_DESCRIPTION_MAX_LENGTH + " characters"
    )
    private String description;

    @Getter
    @Setter
    @NotNull(message = "diet tags cannot be null")
    @Size(max = Constants.TAGS_MAX_LENGTH, message = "diet tags must have max " + Constants.TAGS_MAX_LENGTH + " elements")
    private List<
        @Size(
            min = Constants.STRING_FILTER_MIN_LENGTH,
            max = Constants.STRING_FILTER_MAX_LENGTH,
            message = "diet tag must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
        )
    String> tags;

    @Getter
    @Setter
    @NotNull(message = "goal cannot be null")
    @NotBlank(message = "goal cannot be an empty string")
    @Size(
        min = Constants.DIET_GOAL_MIN_LENGTH,
        max = Constants.DIET_GOAL_MAX_LENGTH,
        message = "goal must have between " + Constants.DIET_GOAL_MIN_LENGTH + " and " + Constants.DIET_GOAL_MAX_LENGTH + " characters"
    )
    private String goal;

    @Getter
    @Setter
    @NotNull(message = "type cannot be null")
    @NotBlank(message = "type cannot be an empty string")
    @Size(
        min = Constants.DIET_TYPE_MIN_LENGTH,
        max = Constants.DIET_TYPE_MAX_LENGTH,
        message = "type must have between " + Constants.DIET_TYPE_MIN_LENGTH + " and " + Constants.DIET_TYPE_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.DIET_TYPE_REGEXP, message = "type must respect to the following pattern: " + Constants.DIET_TYPE_REGEXP)
    private String type;
    
    @Getter
    @Setter
    @NotNull(message = "startsOn cannot be null")
    @NotBlank(message = "startsOn cannot be an empty string")
        @Size(
        min = Constants.DIET_STARTS_ON_MIN_LENGTH,
        max = Constants.DIET_STARTS_ON_MAX_LENGTH,
        message = "startsOn must have between " + Constants.DIET_STARTS_ON_MIN_LENGTH + " and " + Constants.DIET_STARTS_ON_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ISO8601_FORMAT_REGEXP, message = "startsOn must respect to the following pattern: " + Constants.ISO8601_FORMAT_REGEXP)
    private String startsOn;

    @Getter
    @Setter
    @NotNull(message = "duration cannot be null")
    @Min(value = Constants.MIN_DIET_DURATION_VALUE, message = "duration must be greater than, or equal to " + Constants.MIN_DIET_DURATION_VALUE)
    @Max(value = Constants.MAX_DIET_DURATION_VALUE, message = "duration must be lower than, or equal to " + Constants.MAX_DIET_DURATION_VALUE)
    private Integer duration;

    @Getter
    @Setter
    @NotNull(message = "isArchived cannot be null")
    private Boolean isArchived;

    @Getter
    @Setter
    @NotNull(message = "accountId cannot be null")
    @NotBlank(message = "accountId cannot be an empty string")@Size(
        min = Constants.ACCOUNT_ID_MIN_LENGTH,
        max = Constants.ACCOUNT_ID_MAX_LENGTH,
        message = "accountId must have between " + Constants.ACCOUNT_ID_MIN_LENGTH + " and " + Constants.ACCOUNT_ID_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.UUID_REGEXP, message = "accountId must respect to the following pattern: " + Constants.UUID_REGEXP)
    private String accountId;

    @Getter
    @Setter
    @Valid
    @NotNull(message = "dietForActivities cannot be null")
    @Size(max = Constants.DIET_FOR_ACTIVITIES_MAX_LENGTH, message = "dietForActivities must have max " + Constants.DIET_FOR_ACTIVITIES_MAX_LENGTH + " elements")
    private List<UpdateDietActivityDto> dietForActivities;
    
    @Getter
    @Setter
    @Valid
    @NotNull(message = "meals cannot be null")
    @Size(max = Constants.DIET_MEALS_MAX_LENGTH, message = "meals must have max " + Constants.DIET_MEALS_MAX_LENGTH + " elements")
    private List<UpdateDietMealDto> meals;
}
