package com.academix.academix.log.dto.response;

import com.academix.academix.document.request.enums.DocumentStatus;
import com.academix.academix.document.request.enums.DocumentType;
import com.academix.academix.log.enums.ActorRole;
import com.academix.academix.log.enums.DocumentAction;
import com.academix.academix.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class DocumentRequestAuditDetailResponseDTO {
    private Long auditId;

    private Long documentRequestId;
    private String purpose;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime requestedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime pickUpDate;

    private Long requesterId;
    private String requestedByName;
    private String requestedByEmail;

    private LocalDateTime performedAt;
    @Enumerated(EnumType.STRING)
    private DocumentAction action; // e.g. "APPROVED", "REJECTED", "CANCELLED", "SET_TO_READY"
    @Enumerated(EnumType.STRING)
    private ActorRole actorRole;
    private String remark; // optional: reason, comment, etc.
}
