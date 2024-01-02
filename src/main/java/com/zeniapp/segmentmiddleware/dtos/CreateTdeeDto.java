package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.constants.Constants;
import jakarta.validation.Valid;
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
public class CreateTdeeDto {
    @Getter
    @Setter
    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be an empty string")
    @Size(
        min = Constants.TDEE_TITLE_MIN_LENGTH,
        max = Constants.TDEE_TITLE_MAX_LENGTH,
        message = "title must have between " + Constants.TDEE_TITLE_MIN_LENGTH + " and " + Constants.TDEE_TITLE_MAX_LENGTH + " characters"
    )
    private String title;

    @Getter
    @Setter
    @Size(
        min = Constants.TDEE_DESCRIPTION_MIN_LENGTH,
        max = Constants.TDEE_DESCRIPTION_MAX_LENGTH,
        message = "description must have between " + Constants.TDEE_DESCRIPTION_MIN_LENGTH + " and " + Constants.TDEE_DESCRIPTION_MAX_LENGTH + " characters"
    )
    private String description;

    @Getter
    @Setter
    @NotNull(message = "performedOn cannot be null")
    @NotBlank(message = "performedOn cannot be an empty string")
        @Size(
        min = Constants.TDEE_PERFORMED_ON_MIN_LENGTH,
        max = Constants.TDEE_PERFORMED_ON_MAX_LENGTH,
        message = "performedOn must have between " + Constants.TDEE_PERFORMED_ON_MIN_LENGTH + " and " + Constants.TDEE_PERFORMED_ON_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ISO8601_FORMAT_REGEXP, message = "performedOn must respect to the following pattern: " + Constants.ISO8601_FORMAT_REGEXP)
    private String performedOn;

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
    @NotNull(message = "tdee tags cannot be null")
    @Size(max = Constants.TAGS_MAX_LENGTH, message = "tdee tags must have max " + Constants.TAGS_MAX_LENGTH + " elements")
    private List<
        @Size(
            min = Constants.STRING_FILTER_MIN_LENGTH,
            max = Constants.STRING_FILTER_MAX_LENGTH,
            message = "tdee tag must have between " + Constants.STRING_FILTER_MIN_LENGTH + " and " + Constants.STRING_FILTER_MAX_LENGTH + " characters"
        )
    String> tags;

    @Getter
    @Setter
    @Valid
    @NotNull(message = "personalInfo cannot be null")
    private CreateTdeePersonalInfoDto personalInfo;
    
    @Getter
    @Setter
    @NotBlank(message = "idealWeightFormula cannot be an empty string")
    @Size(
        min = Constants.IDEAL_WEIGHT_FORMULA_MIN_LENGTH,
        max = Constants.IDEAL_WEIGHT_FORMULA_MAX_LENGTH,
        message = "idealWeightFormula must have between " + Constants.IDEAL_WEIGHT_FORMULA_MIN_LENGTH + " and " + Constants.IDEAL_WEIGHT_FORMULA_MAX_LENGTH + " characters"
    )
    private String idealWeightFormula; // nullable, the idealWeight can be inserted manually
    
    @Getter
    @Setter
    @NotNull(message = "basalMetabolismRateFormula cannot be null")
    @NotBlank(message = "basalMetabolismRateFormula cannot be an empty string")
    @Size(
        min = Constants.BASAL_METABOLIC_RATE_FORMULA_MIN_LENGTH,
        max = Constants.BASAL_METABOLIC_RATE_FORMULA_MAX_LENGTH,
        message = "basalMetabolismRateFormula must have between " + Constants.BASAL_METABOLIC_RATE_FORMULA_MIN_LENGTH + " and " + Constants.BASAL_METABOLIC_RATE_FORMULA_MAX_LENGTH + " characters"
    )
    private String basalMetabolismRateFormula;
}