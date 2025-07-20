package com.academix.academix.document.mapper;

import com.academix.academix.document.dto.request.DocumentRequestPayloadDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DocumentRemarkMapper.class, UserMapper.class})
public interface DocumentRequestMapper {

    //@Mapping(source = "userDetailedInfo", target = "requestedBy")
    // For Creation
    @Mapping(target = "remarks", ignore = true)
    DocumentRequest toDocumentRequestEntity(DocumentRequestPayloadDTO documentRequestDTO);

    //@Mapping(target = "requestedBy" , expression = "java(documentRequest.getRequestedBy().getName())")
//    @Mapping(source = "requestedBy", target = "userDetailedInfo")
    DocumentRequestResponseDTO toDocumentRequestResponseDTO(DocumentRequest documentRequest);

    // For admin/registrar to see all the document request in detailed format
    List<DocumentRequestResponseDTO> toListOfDocumentRequestResponseDTO(List<DocumentRequest> documentRequestList);

    @Mapping(source = "requestedBy", target = "requestedBy")
    // For listing document request
    DocumentRequestResponseListDTO toDocumentRequestResponseListDTO(DocumentRequest documentRequest);

    List<DocumentRequestResponseListDTO> toDocumentRequestResponseListDTO(List<DocumentRequest> documentRequestList);

    // For update
    @Mapping(target = "remarks", ignore = true) // only update its own comment
    void updateDocumentRequestEntityFromDTO(DocumentRequestPayloadDTO documentRequestDTO, @MappingTarget DocumentRequest documentRequestEntity);
}
