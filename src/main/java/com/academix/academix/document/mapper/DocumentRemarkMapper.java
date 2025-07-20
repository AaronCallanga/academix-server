package com.academix.academix.document.mapper;

import com.academix.academix.document.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.entity.DocumentRemark;
import com.academix.academix.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface DocumentRemarkMapper {

    //DocumentRemark toDocumentRemarkEntity(DocumentRemarkRequestDTO documentRemarkRequestDTO);

    //List<DocumentRemark> toDocumentRemarkListEntity(List<DocumentRemarkRequestDTO> documentRemarkRequestDTOList);

    DocumentRemarkResponseDTO toDocumentRemarkResponseDTO(DocumentRemark documentRemark);

    List<DocumentRemarkResponseDTO> toDocumentRemarkResponseDTOList(List<DocumentRemark> documentRemarkList);

}
