package com.academix.academix.document.dto.request;

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

    private String documentType; // F-137, Good Moral, etc.

    private String purpose; // Enrollment, transfer, job, etc.

    private LocalDateTime pickUpDate;       // approved date by admin/registrar
}
