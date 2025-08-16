package com.academix.academix.document.remark.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentRemarkRequestDTO {     // Both for update/ delete
    @NotBlank(message = "Remark content must not be empty")
    private String content;
}