package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.constants.Constants;
import com.zeniapp.segmentmiddleware.enums.ActivityType;
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
public class CreateTrainingDto {
    @Getter
    @Setter
    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be an empty string")
    @Size(
        min = Constants.TRAINING_TITLE_MIN_LENGTH,
        max = Constants.TRAINING_TITLE_MAX_LENGTH,
        message = "title must have between " + Constants.TRAINING_TITLE_MIN_LENGTH + " and " + Constants.TRAINING_TITLE_MAX_LENGTH + " characters"
    )
    private String title;

    @Getter
    @Setter
    @Size(
        min = Constants.TRAINING_DESCRIPTION_MIN_LENGTH,
        max = Constants.TRAINING_DESCRIPTION_MAX_LENGTH,
        message = "description must have between " + Constants.TRAINING_DESCRIPTION_MIN_LENGTH + " and " + Constants.TRAINING_DESCRIPTION_MAX_LENGTH + " characters"
    )
    private String description;

    @Getter
    @Setter
    @NotNull(message = "type cannot be null")
    @NotBlank(message = "type cannot be an empty string")
    @Size(
        min = Constants.TRAINING_TYPE_MIN_LENGTH,
        max = Constants.TRAINING_TYPE_MAX_LENGTH,
        message = "type must have between " + Constants.TRAINING_TYPE_MIN_LENGTH + " and " + Constants.TRAINING_TYPE_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.TRAINING_TYPE_REGEXP, message = "type must respect to the following pattern: " + Constants.TRAINING_TYPE_REGEXP)
    private String type;

    @Getter
    @Setter
    @NotNull(message = "startsOn cannot be null")
    @NotBlank(message = "startsOn cannot be an empty string")
        @Size(
        min = Constants.TRAINING_STARTS_ON_MIN_LENGTH,
        max = Constants.TRAINING_STARTS_ON_MAX_LENGTH,
        message = "type must have between " + Constants.TRAINING_STARTS_ON_MIN_LENGTH + " and " + Constants.TRAINING_STARTS_ON_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.TRAINING_TYPE_REGEXP, message = "type must respect to the following pattern: " + Constants.TRAINING_STARTS_ON_REGEXP)
    private String startsOn;

    @Getter
    @Setter
    @NotNull(message = "duration cannot be null")
    @Min(value = Constants.MIN_TRAINING_DURATION_VALUE, message = "duration must be greater than, or equal to " + Constants.MIN_TRAINING_DURATION_VALUE)
    @Max(value = Constants.MAX_TRAINING_DURATION_VALUE, message = "duration must be lower than, or equal to " + Constants.MAX_TRAINING_DURATION_VALUE)
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
        message = "type must have between " + Constants.ACCOUNT_ID_MIN_LENGTH + " and " + Constants.ACCOUNT_ID_MAX_LENGTH + " characters"
    )
    private String accountId;

    @Getter
    @Setter
    @NotNull(message = "trainingForActivities cannot be null")
    @Size(max = Constants.TRAINING_FOR_ACTIVITIES_MAX_LENGTH, message = "trainingForActivities must have max " + Constants.TRAINING_FOR_ACTIVITIES_MAX_LENGTH + " elements")
    private List<CreateTrainingActivityDto> trainingForActivities;

    @Getter
    @Setter
    @NotNull(message = "sessions cannot be null")
    @Size(max = Constants.TRAINING_SESSIONS_MAX_LENGTH, message = "sessions must have max " + Constants.TRAINING_SESSIONS_MAX_LENGTH + " elements")
    private List<CreateTrainingSessionDto> sessions;

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public class CreateTrainingActivityDto  {
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
        private ActivityType type;

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
}
