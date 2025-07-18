package com.academix.academix.document.dto;

import com.academix.academix.document.entity.DocumentRemark;
import com.academix.academix.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class DocumentRequestDTO {
    private Long id;

    private String documentType; // F-137, Good Moral, etc.

    private String purpose; // Enrollment, transfer, job, etc.

    private String status; // PENDING, APPROVED, REJECTED    can modify by admin/registrar

    private LocalDateTime requestDate;

    private LocalDateTime pickUpDate;       // approved date by admin/registrar

    private String requestedBy;     // user name

    private List<DocumentRemark> remarks;
}
