package com.academix.academix.log.mapper;

import com.academix.academix.log.dto.response.DocumentRequestAuditDetailResponseDTO;
import com.academix.academix.log.entity.DocumentRequestAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentRequestAuditMapper {
    DocumentRequestAuditDetailResponseDTO toDocumentRequestAuditResponseDTO(DocumentRequestAudit documentRequestAudit);

    List<DocumentRequestAuditDetailResponseDTO> toDocumentRequestAuditResponseDTOList(List<DocumentRequestAudit> documentRequestAuditList);
}
