package com.zeniapp.segmentmiddleware.dtos;

import com.zeniapp.segmentmiddleware.constants.Constants;
import jakarta.validation.constraints.Email;
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
public class UpdateAccountDto {
    @Setter
    @Getter
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be an empty string")
    @Size(
        min = Constants.EMAIL_MIN_LENGTH,
        max = Constants.EMAIL_MAX_LENGTH,
        message = "email must have between " + Constants.EMAIL_MIN_LENGTH + " and " + Constants.EMAIL_MAX_LENGTH + " characters"
    )
    @Email(message = "email must be well-formatted")
    private String email;

    @Setter
    @Getter
    @NotNull(message = "username cannot be null")
    @NotBlank(message = "username cannot be an empty string")
    @Size(
        min = Constants.USERNAME_MIN_LENGTH,
        max = Constants.USERNAME_MAX_LENGTH,
        message = "username must have between " + Constants.USERNAME_MIN_LENGTH + " and " + Constants.USERNAME_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.USERNAME_REGEXP, message = "username can only contain of letters, or numbers")
    private String username;

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

    @Setter
    @Getter
    @NotNull(message = "isConfirmed cannot be null")
    private Boolean isConfirmed;

    @Setter
    @Getter
    @NotNull(message = "isBlocked cannot be null")
    private Boolean isBlocked;

    @Setter
    @Getter
    @Size(
        min = Constants.PASSWORD_MIN_LENGTH,
        max = Constants.PASSWORD_MAX_LENGTH,
        message = "password must have between " + Constants.PASSWORD_MIN_LENGTH + " and " + Constants.PASSWORD_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.PASSWORD_REGEXP, message = "password must contain at least one uppercase letter, one lowercase letter, one number and one special character (@$!%*?&)")
    private String password;

    @Setter
    @Getter
    @NotNull(message = "role cannot be null")
    @NotBlank(message = "role cannot be an empty string")
    @Size(
        min = Constants.ROLE_MIN_LENGTH,
        max = Constants.ROLE_MAX_LENGTH,
        message = "role must have between " + Constants.ROLE_MIN_LENGTH + " and " + Constants.ROLE_MAX_LENGTH + " characters"
    )
    @Pattern(regexp = Constants.ROLE_REGEXP, message = "role must respect to the following pattern: " + Constants.ROLE_REGEXP)
    private String role;
}
