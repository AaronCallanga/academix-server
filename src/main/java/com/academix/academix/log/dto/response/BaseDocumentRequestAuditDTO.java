package com.academix.academix.log.dto.response;

import com.academix.academix.log.enums.DocumentAction;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public abstract class BaseDocumentRequestAuditDTO {
    private Long auditId;
    private Long documentRequestId;
    private String action;
    private String actorRole;
    private String remark;

    private Long performedById;
    private String performedByName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime performedAt;
}
