package com.academix.academix.document.mapper;

import com.academix.academix.document.dto.DocumentRequestDTO;
import com.academix.academix.document.dto.DocumentRequestListDTO;
import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DocumentRemarkMapper.class, UserMapper.class})
public interface DocumentRequestMapper {
    //@Mapping(target = "requestedBy", ignore = true)
    @Mapping(source = "userDetailedInfo", target = "requestedBy")
    DocumentRequest toDocumentRequestEntity(DocumentRequestDTO documentRequestDTO);
    //@Mapping(target = "requestedBy" , expression = "java(documentRequest.getRequestedBy().getName())")
    @Mapping(source = "requestedBy", target = "userDetailedInfo")
    DocumentRequestDTO toDocumentRequestDTO(DocumentRequest documentRequest);
    List<DocumentRequestDTO> toDocumentRequestDTOList(List<DocumentRequest> documentRequestList);

    @Mapping(source = "requestedBy", target = "userInfo")
    DocumentRequestListDTO toDocumentRequestListDTO(DocumentRequest documentRequest);
    List<DocumentRequestListDTO> toDocumentRequestListDTO(List<DocumentRequest> documentRequestList);
    void updateDocumentRequestEntityFromDTO(DocumentRequestDTO documentRequestDTO, @MappingTarget DocumentRequest documentRequestEntity);
}
