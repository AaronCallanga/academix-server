package com.academix.academix.log.mapper;

import com.academix.academix.log.dto.response.DocumentRequestAuditResponseDTO;
import com.academix.academix.log.entity.DocumentRequestAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentRequestAuditMapper {
    @Mapping(source = "documentRequest.id", target = "documentRequestId")
    @Mapping(source = "performedBy.id", target = "performedById")
    @Mapping(source = "performedBy.name", target = "performedByName")
    DocumentRequestAuditResponseDTO toDocumentRequestAuditResponseDTO(DocumentRequestAudit documentRequestAudit);
}
