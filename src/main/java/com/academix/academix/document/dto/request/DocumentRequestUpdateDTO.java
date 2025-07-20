package com.academix.academix.document.dto.request;


import com.academix.academix.document.entity.DocumentRemark;
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
public class DocumentRequestUpdateDTO {

    private String documentType; // F-137, Good Moral, etc.

    private String purpose; // Enrollment, transfer, job, etc.

    private String status; // PENDING, APPROVED, REJECTED    can modify by admin/registrar

    private LocalDateTime requestDate;

    private LocalDateTime pickUpDate;       // approved date by admin/registrar

    private List<DocumentRemark> remarks;
}
