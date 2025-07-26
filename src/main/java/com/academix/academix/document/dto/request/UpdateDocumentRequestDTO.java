package com.academix.academix.document.dto.request;

import com.academix.academix.document.enums.DocumentType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateDocumentRequestDTO {

    @NotNull(message = "Document type is required")
    private DocumentType documentType; // F-137, Good Moral, etc.

    @Size(min = 5, max = 500)
    @NotBlank(message = "Purpose is required")
    private String purpose; // Enrollment, transfer, job, etc.

    @NotNull(message = "Pickup date is required")
    @Future(message = "Pickup date must be in the future")
    private LocalDateTime pickUpDate;       // approved date by admin/registrar
}
