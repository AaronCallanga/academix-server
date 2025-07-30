package com.academix.academix.log.dto.response;

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
    private String remark;
    private LocalDateTime performedAt;
}
