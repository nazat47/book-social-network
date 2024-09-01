package com.nazat.book_network.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {
    @NotEmpty(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Email not in correct format")
    private String email;

    @NotEmpty(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be more than 8 characters long")
    private String password;
}
