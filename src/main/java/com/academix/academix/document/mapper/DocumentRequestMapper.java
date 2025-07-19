package com.academix.academix.document.mapper;

import com.academix.academix.document.dto.DocumentRequestDTO;
import com.academix.academix.document.dto.DocumentRequestListDTO;
import com.academix.academix.document.entity.DocumentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DocumentRemarkMapper.class})
public interface DocumentRequestMapper {
    @Mapping(target = "requestedBy", ignore = true)
    DocumentRequest toDocumentRequestEntity(DocumentRequestDTO documentRequestDTO);
    @Mapping(target = "requestedBy" , expression = "java(documentRequest.getRequestedBy().getName())")
    DocumentRequestDTO toDocumentRequestDTO(DocumentRequest documentRequest);
    List<DocumentRequestDTO> toDocumentRequestDTOList(List<DocumentRequest> documentRequestList);
    List<DocumentRequestListDTO> toDocumentRequestListDTO(List<DocumentRequest> documentRequestList);
    void updateDocumentRequestEntityFromDTO(DocumentRequestDTO documentRequestDTO, @MappingTarget DocumentRequest documentRequestEntity);
}
