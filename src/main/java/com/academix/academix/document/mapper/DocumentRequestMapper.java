package com.academix.academix.document.mapper;

import com.academix.academix.document.dto.DocumentRequestDTO;
import com.academix.academix.document.dto.DocumentRequestListDTO;
import com.academix.academix.document.entity.DocumentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = DocumentRemarkMapper.class)
public interface DocumentRequestMapper {
    DocumentRequest toDocumentRequestEntity(DocumentRequestDTO documentRequestDTO);
    DocumentRequestDTO toDocumentRequestDTO(DocumentRequest documentRequest);
    List<DocumentRequestDTO> toDocumentRequestDTOList(List<DocumentRequest> documentRequestList);
    List<DocumentRequestListDTO> toDocumentRequestListDTO(List<DocumentRequest> documentRequestList);
    void updateDocumentRequestEntityFromDTO(DocumentRequestDTO documentRequestDTO, @MappingTarget DocumentRequest documentRequestEntity);
}
