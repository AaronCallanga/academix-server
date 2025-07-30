package com.academix.academix.log.dto.response;

import com.academix.academix.log.enums.ActorType;
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
public class DocumentRequestAuditResponseDTO {
    private Long id;
    private Long documentRequestId;

    private Long performedById;
    private String performedByName; // optional: to avoid frontend joining user names

    private String action;
    private String actorType;
    private String remark;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime performedAt;
}
