package com.academix.academix.document.dto.request;


import com.academix.academix.document.enums.DocumentType;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateDocumentRequestDTO {        // For updating and creating document request

//    private Long userId; // requestedBy get from jwt
    @NotNull(message = "Document type is required")
    private DocumentType documentType; // F-137, Good Moral, etc.

    @Size(min = 5, max = 500)
    @NotBlank(message = "Purpose is required")
    private String purpose; // Enrollment, transfer, job, etc.

    @NotNull(message = "Pickup date is required")
    @Future(message = "Pickup date must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime pickUpDate;       // approved date by admin/registrar

    private List<DocumentRemarkRequestDTO> remarks;
}

// Should be done in service
//    private String status; // default: PENDING, APPROVED, REJECTED    can modify by admin/registrar
//
//    private LocalDateTime requestDate; LocalDateTime.now();