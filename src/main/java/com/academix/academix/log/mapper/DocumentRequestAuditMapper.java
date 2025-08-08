package com.academix.academix.log.mapper;

import com.academix.academix.log.dto.response.DocumentRequestAuditDetailResponseDTO;
import com.academix.academix.log.dto.response.DocumentRequestAuditListResponseDTO;
import com.academix.academix.log.entity.DocumentRequestAudit;
import com.academix.academix.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface DocumentRequestAuditMapper {

    @Mapping(source = "performedBy.id", target = "performedById")
    @Mapping(source = "performedBy.name", target = "performedByName")
    DocumentRequestAuditDetailResponseDTO toDocumentRequestAuditResponseDTO(DocumentRequestAudit documentRequestAudit);

    @Mapping(source = "performedBy.id", target = "performedById")
    @Mapping(source = "performedBy.name", target = "performedByName")
    List<DocumentRequestAuditListResponseDTO> toDocumentRequestAuditResponseDTOList(List<DocumentRequestAudit> documentRequestAuditList);
}
