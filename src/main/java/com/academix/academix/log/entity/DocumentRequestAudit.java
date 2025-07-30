package com.academix.academix.log.entity;

import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.log.enums.ActorType;
import com.academix.academix.log.enums.DocumentAction;
import com.academix.academix.user.entity.User;
import jakarta.persistence.Entity;
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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DocumentRequestAudit {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private DocumentRequest documentRequest;

    @ManyToOne
    private User performedBy;

    @Enumerated(EnumType.STRING)
    private DocumentAction action; // e.g. "APPROVED", "REJECTED", "CANCELLED", "SET_TO_READY"

    @Enumerated(EnumType.STRING)
    private ActorType actorType;

    private String remark; // optional: reason, comment, etc.

    private LocalDateTime performedAt;
}
