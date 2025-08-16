package com.academix.academix.document.remark.mapper;

import com.academix.academix.document.remark.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.remark.entity.DocumentRemark;
import com.academix.academix.user.mapper.UserMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface DocumentRemarkMapper {

    DocumentRemarkResponseDTO toDocumentRemarkResponseDTO(DocumentRemark documentRemark);

    List<DocumentRemarkResponseDTO> toDocumentRemarkResponseDTOList(List<DocumentRemark> documentRemarkList);

}
