package com.academix.academix.document.dto.request;


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

    private String documentType; // F-137, Good Moral, etc.

    private String purpose; // Enrollment, transfer, job, etc.

    private LocalDateTime pickUpDate;       // approved date by admin/registrar

    private List<DocumentRemarkRequestDTO> remarks;
}

// Should be done in service
//    private String status; // default: PENDING, APPROVED, REJECTED    can modify by admin/registrar
//
//    private LocalDateTime requestDate; LocalDateTime.now();