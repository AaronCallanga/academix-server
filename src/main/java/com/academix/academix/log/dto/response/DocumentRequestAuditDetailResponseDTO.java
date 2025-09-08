package com.academix.academix.log.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class DocumentRequestAuditDetailResponseDTO extends BaseDocumentRequestAuditDTO{
    private String purpose;
    private String documentType;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime requestedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime pickUpDate;

    private Long requesterId;
    private String requestedByName;
}
