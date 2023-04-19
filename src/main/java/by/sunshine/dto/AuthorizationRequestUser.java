package by.sunshine.dto;

import by.sunshine.annotation.TrimLength;
import jakarta.validation.constraints.Email;
import lombok.Data;

import static by.sunshine.constant.Error.*;

@Data
public class AuthorizationRequestUser {

    @Email(message = INCORRECT_AUTH)
    private String email;

    @TrimLength(message = INCORRECT_AUTH)
    private String password;
}
