package com.academix.academix.log.entity;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.enums.DocumentStatus;
import com.academix.academix.document.request.enums.DocumentType;
import com.academix.academix.log.enums.ActorRole;
import com.academix.academix.log.enums.DocumentAction;
import com.academix.academix.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DocumentRequestAudit {
    @Id
    @GeneratedValue
    private Long auditId;

    private Long requesterId;
    private String requestedByName;
    private Long documentRequestId;
    // snapshot of important fields at time of action
    private String purpose;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;
    private LocalDateTime requestedAt;
    private LocalDateTime pickUpDate;

    private Long performedById;
    private String performedByName;
    private LocalDateTime performedAt;
    @Enumerated(EnumType.STRING)
    private DocumentAction action; // e.g. "APPROVED", "REJECTED", "CANCELLED", "SET_TO_READY"
    @Enumerated(EnumType.STRING)
    private ActorRole actorRole;
    private String remark; // optional: reason, comment, etc.

}
