package com.zeniapp.segmentmiddleware.dtos;

import com.zeniapp.segmentmiddleware.constants.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDto {
    @Getter
    @Setter
    @NotNull(message = "identifier cannot be null")
    @NotBlank(message = "identifier cannot be an empty string")
    @Size(
        min = Constants.IDENTIFIER_MIN_LENGTH,
        max = Constants.IDENTIFIER_MAX_LENGTH,
        message = "identifier must have between " + Constants.IDENTIFIER_MIN_LENGTH + " and " + Constants.IDENTIFIER_MAX_LENGTH + " characters"
    )
    private String identifier;

    @Getter
    @Setter
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be an empty string")
    @Size(
        min = Constants.PASSWORD_MIN_LENGTH,
        max = Constants.PASSWORD_MAX_LENGTH,
        message = "password must have between " + Constants.PASSWORD_MIN_LENGTH + " and " + Constants.PASSWORD_MAX_LENGTH + " characters"
    )
    private String password;
}
