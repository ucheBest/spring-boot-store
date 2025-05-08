package com.codewithmosh.store.auth;

import com.codewithmosh.store.users.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginUserRequest {
    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    @Lowercase(message = "email must be lowercase")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}
