package com.academix.academix.document.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public class DocumentRequestAdminUpdateDTO {

    private String status;  // PENDING, APPROVED, REJECTED

    private LocalDateTime pickUpDate;

    private List<DocumentRemarkRequestDTO> remarks;
}
