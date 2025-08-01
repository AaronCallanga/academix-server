package com.academix.academix.document.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReasonDTO {
    @NotBlank(message = "Reason must not be provided")
    String reason;
}
