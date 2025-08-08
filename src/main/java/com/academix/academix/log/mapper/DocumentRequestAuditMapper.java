package com.academix.academix.log.mapper;

import com.academix.academix.log.dto.response.DocumentRequestAuditDetailResponseDTO;
import com.academix.academix.log.dto.response.DocumentRequestAuditListResponseDTO;
import com.academix.academix.log.entity.DocumentRequestAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentRequestAuditMapper {

    @Mapping(source = "performedBy.id", target = "performedById")
    @Mapping(source = "performedBy.name", target = "performedByName")
    DocumentRequestAuditDetailResponseDTO toDocumentRequestAuditResponseDTO(DocumentRequestAudit documentRequestAudit);

    List<DocumentRequestAuditListResponseDTO> toDocumentRequestAuditResponseDTOList(List<DocumentRequestAudit> documentRequestAuditList);
}
