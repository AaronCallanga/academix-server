package com.academix.academix.security.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequestDTO {
    @Email(message = "Valid email is required")
    private String email;
    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must be greater than 5 characters")  //can create anotation that password should consist of complex characters
    private String password;
    @NotBlank(message = "Learner Reference Number(LRN) must not be blank")
    private String lrn;
    @NotBlank(message = "Name must not be provided")
    private String name;
    @NotBlank(message = "Contact number must not be blank")
    private String contactNumber;
    @NotBlank(message = "User verification status must not be blank")
    private boolean isVerified;
    @NotBlank(message = "Roles must not be blank")
    Set<String> roles;          // maybe anotation that ensure only the specified role can be registered
}
