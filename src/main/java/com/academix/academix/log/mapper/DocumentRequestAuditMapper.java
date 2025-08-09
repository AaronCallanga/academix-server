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

    DocumentRequestAuditDetailResponseDTO toDocumentRequestAuditResponseDTO(DocumentRequestAudit documentRequestAudit);

    List<DocumentRequestAuditListResponseDTO> toDocumentRequestAuditResponseDTOList(List<DocumentRequestAudit> documentRequestAuditList);
}
