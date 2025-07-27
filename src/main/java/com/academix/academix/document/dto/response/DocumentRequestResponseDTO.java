package com.academix.academix.document.dto.response;

import com.academix.academix.document.entity.DocumentRemark;
import com.academix.academix.user.dto.UserDetailedInfoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class DocumentRequestResponseDTO {  // Response DTO

    // For individual request information
    private Long id;

    private String documentType; // F-137, Good Moral, etc.

    private String purpose; // Enrollment, transfer, job, etc.

    private String status; // PENDING, APPROVED, REJECTED    can modify by admin/registrar

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime requestDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime pickUpDate;       // approved date by admin/registrar

    private UserDetailedInfoDTO requestedBy;

    private List<DocumentRemarkResponseDTO> remarks;
}
