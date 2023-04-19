package by.sunshine.dto;

import by.sunshine.annotation.TrimLength;
import jakarta.validation.constraints.*;
import lombok.Data;

import static by.sunshine.constant.Error.*;

@Data
public class RegistrationRequestUser {

    @NotBlank(message = EMPTY_NAME)
    @NotNull
    private String name;

    @Email(message = INVALID_EMAIL)
    @NotNull
    private String email;

    @TrimLength(message = INVALID_TRIM)
    @NotNull
    private String password;

    @TrimLength(message = INVALID_TRIM)
    @NotNull
    private String confirmedPassword;
}
