package com.zeniapp.segmentmiddleware.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.zeniapp.segmentmiddleware.constants.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateSessionDto {
    @Setter
    @Getter
    @NotNull(message = "identifier cannot be null")
    @NotBlank(message = "identifier cannot be an empty string")
    @Size(
        min = Constants.IDENTIFIER_MIN_LENGTH,
        max = Constants.IDENTIFIER_MAX_LENGTH,
        message = "identifier must have between " + Constants.IDENTIFIER_MIN_LENGTH + " and " + Constants.IDENTIFIER_MAX_LENGTH + " characters"
    )
    private String identifier;
}
