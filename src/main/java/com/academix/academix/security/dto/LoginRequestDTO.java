package com.academix.academix.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequestDTO {
    @Email(message = "Valid email is required")
    private String email;
    @NotBlank(message = "Password must not be blank")
    private String password;
}
