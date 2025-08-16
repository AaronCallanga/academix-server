package com.academix.academix.document.request.mapper;

import com.academix.academix.document.request.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.request.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.remark.mapper.DocumentRemarkMapper;
import com.academix.academix.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DocumentRemarkMapper.class, UserMapper.class})
public interface DocumentRequestMapper {

    // For Creation
    @Mapping(target = "remarks", ignore = true)
    DocumentRequest toDocumentRequestEntity(CreateDocumentRequestDTO documentRequestDTO);

    DocumentRequestResponseDTO toDocumentRequestResponseDTO(DocumentRequest documentRequest);

    // For admin/registrar to see all the document request in detailed format
    List<DocumentRequestResponseDTO> toListOfDocumentRequestResponseDTO(List<DocumentRequest> documentRequestList);

    // For listing document request
    @Mapping(source = "requestedBy", target = "requestedBy")
    DocumentRequestResponseListDTO toDocumentRequestResponseListDTO(DocumentRequest documentRequest);

    List<DocumentRequestResponseListDTO> toDocumentRequestResponseListDTO(List<DocumentRequest> documentRequestList);

    // For update
    void updateDocumentRequestEntityFromDTO(UpdateDocumentRequestDTO documentRequestDTO, @MappingTarget DocumentRequest documentRequestEntity);
}
