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
public class UpdateTrainingActivityDto  {
    @Getter
    @Setter
    @NotNull(message = "activity name cannot be null")
    @NotBlank(message = "activity name cannot be an empty string")
    @Size(
        min = Constants.ACTIVITY_NAME_MIN_LENGTH,
        max = Constants.ACTIVITY_NAME_MAX_LENGTH,
        message = "activity name must have between " + Constants.ACTIVITY_NAME_MIN_LENGTH + " and " + Constants.ACTIVITY_NAME_MAX_LENGTH + " characters"
    )
    private String name;
    
    @Getter
    @Setter
    @NotNull(message = "activity type cannot be null")
    @NotBlank(message = "activity type cannot be an empty string")
    @Size(
        min = Constants.ACTIVITY_TYPE_MIN_LENGTH,
        max = Constants.ACTIVITY_TYPE_MAX_LENGTH,
        message = "activity type must have between " + Constants.ACTIVITY_TYPE_MIN_LENGTH + " and " + Constants.ACTIVITY_TYPE_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ACTIVITY_TYPE_REGEXP, message = "activity type must respect to the following pattern: " + Constants.ACTIVITY_TYPE_REGEXP)
    private String type;

    @Getter
    @Setter
    @NotNull(message = "activity mets cannot be null")
    @Min(value = Constants.MIN_METS_VALUE, message = "activity mets must be greater than, or equal to " + Constants.MIN_METS_VALUE)
    @Max(value = Constants.MAX_METS_VALUE, message = "activity mets must be lower than, or equal to " + Constants.MAX_METS_VALUE)
    private Integer mets;

    @Getter
    @Setter
    @NotNull(message = "activity tags cannot be null")
    @Size(max = Constants.TAGS_MAX_LENGTH, message = "activity tags must have max " + Constants.TAGS_MAX_LENGTH + " elements")
    private List<
        @Size(
            min = Constants.STRING_FILTER_MIN_LENGTH,
            max = Constants.STRING_FILTER_MAX_LENGTH,
            message = "activity tag must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
        )
    String> tags;
}