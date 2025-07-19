package com.academix.academix.document.mapper;

import com.academix.academix.document.dto.DocumentRemarkDTO;
import com.academix.academix.document.entity.DocumentRemark;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentRemarkMapper {
    DocumentRemark toDocumentRemarkEntity(DocumentRemarkDTO documentRemarkDTO);
    DocumentRemarkDTO toDocumentRemarkDTO(DocumentRemark documentRemark);
    List<DocumentRemarkDTO> toDocumentRemarkDTOList(List<DocumentRemark> documentRemarkList);
}
