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
public class CreateTdeePersonalInfoDto {
    @Setter
    @Getter
    @NotNull(message = "firstName cannot be null")
    @NotBlank(message = "firstName cannot be an empty string")
    @Size(
        min = Constants.FIRST_NAME_MIN_LENGTH,
        max = Constants.FIRST_NAME_MAX_LENGTH,
        message = "firstName must have between " + Constants.FIRST_NAME_MIN_LENGTH + " and " + Constants.FIRST_NAME_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.FIRST_NAME_REGEXP, message = "firstName can contains letters and some special characters (',. -)")
    private String firstName;

    @Setter
    @Getter
    @NotNull(message = "lastName cannot be null")
    @NotBlank(message = "lastName cannot be an empty string")
    @Size(
        min = Constants.LAST_NAME_MIN_LENGTH,
        max = Constants.LAST_NAME_MAX_LENGTH,
        message = "lastName must have between " + Constants.LAST_NAME_MIN_LENGTH + " and " + Constants.LAST_NAME_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.LAST_NAME_REGEXP, message = "lastName can contains letters and some special characters (',. -)")
    private String lastName;
    
    @Getter
    @Setter
    @NotNull(message = "biologicalGender cannot be null")
    @NotBlank(message = "biologicalGender cannot be an empty string")
    @Size(
        min = Constants.BIOLOGICAL_GENDER_MIN_LENGTH,
        max = Constants.BIOLOGICAL_GENDER_MAX_LENGTH,
        message = "biologicalGender must have between " + Constants.BIOLOGICAL_GENDER_MIN_LENGTH + " and " + Constants.BIOLOGICAL_GENDER_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.BIOLOGICAL_GENDER_REGEXP, message = "biologicalGender type must respect to the following pattern: " + Constants.BIOLOGICAL_GENDER_REGEXP)
    private String biologicalGender;
    
    @Getter
    @Setter
    @NotNull(message = "age cannot be null")
    @Min(value = Constants.MIN_AGE_VALUE, message = "age must be greater than, or equal to " + Constants.MIN_AGE_VALUE)
    @Max(value = Constants.MAX_AGE_VALUE, message = "age must be lower than, or equal to " + Constants.MAX_AGE_VALUE)
    private Integer age;

    @Getter
    @Setter
    @NotNull(message = "age cannot be null")
    @Min(value = Constants.MIN_HEIGHT_VALUE, message = "height must be greater than, or equal to " + Constants.MIN_HEIGHT_VALUE)
    @Max(value = Constants.MAX_HEIGHT_VALUE, message = "height must be lower than, or equal to " + Constants.MAX_HEIGHT_VALUE)
    private Float height;

    @Getter
    @Setter
    @NotNull(message = "actualWeight cannot be null")
    @Min(value = Constants.MIN_WEIGHT_VALUE, message = "actualWeight must be greater than, or equal to " + Constants.MIN_WEIGHT_VALUE)
    @Max(value = Constants.MAX_WEIGHT_VALUE, message = "actualWeight must be lower than, or equal to " + Constants.MAX_WEIGHT_VALUE)
    private Float actualWeight;

    @Getter
    @Setter
    @NotNull(message = "activityLevel cannot be null")
    @NotBlank(message = "activityLevel cannot be an empty string")
    @Size(
        min = Constants.ACTIVITY_LEVEL_MIN_LENGTH,
        max = Constants.ACTIVITY_LEVEL_MAX_LENGTH,
        message = "activityLevel must have between " + Constants.ACTIVITY_LEVEL_MIN_LENGTH + " and " + Constants.ACTIVITY_LEVEL_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ACTIVITY_LEVEL_REGEXP, message = "activityLevel type must respect to the following pattern: " + Constants.ACTIVITY_LEVEL_REGEXP)
    private String activityLevel;

    @Getter
    @Setter
    @Valid
    @NotNull(message = "activities cannot be null")
    @Size(max = Constants.TDEE_ACTIVITIES_MAX_LENGTH, message = "activities must have max " + Constants.TDEE_ACTIVITIES_MAX_LENGTH + " elements")
    private List<CreateTdeeActivityDto> activities;
}