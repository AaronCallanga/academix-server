package com.academix.academix.document.remark.facade.impl;

import com.academix.academix.document.remark.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.remark.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.remark.entity.DocumentRemark;
import com.academix.academix.document.remark.facade.api.DocumentRemarkFacade;
import com.academix.academix.document.remark.mapper.DocumentRemarkMapper;
import com.academix.academix.document.remark.repository.DocumentRemarkRepository;
import com.academix.academix.document.remark.service.api.DocumentRemarkService;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.service.api.DocumentRequestService;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.repository.UserRepository;
import com.academix.academix.user.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentRemarkFacadeImpl implements DocumentRemarkFacade {

    private final DocumentRemarkService documentRemarkService;
    private final DocumentRemarkMapper documentRemarkMapper;
    private final DocumentRequestService documentRequestService;
    private final UserService userService;

    @Override
    public Page<DocumentRemarkResponseDTO> getAllDocumentRemarksByRequestId(Long documentRequestId,
                                                                            int size,
                                                                            int page,
                                                                            String sortField,
                                                                            String sortDirection) {
        // Define the page requeest object
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<DocumentRemark> documentRemarkPage = documentRemarkService.getAllDocumentRemarksByRequestId(documentRequestId, pageRequest);

        // Map the paged content (list of document remarks) to DTOs
        List<DocumentRemarkResponseDTO> documentRemarkResponseDTOList =
                documentRemarkMapper.toDocumentRemarkResponseDTOList(documentRemarkPage.getContent());

        // Return the paged data
        return new PageImpl<>(documentRemarkResponseDTOList, pageRequest, documentRemarkPage.getTotalElements());
    }

    @Override
    public DocumentRemarkResponseDTO updateRemark(DocumentRemarkRequestDTO documentRemarkRequestDTO,
                                                  Long documentRemarkId,
                                                  Long documentRequestId) {
        DocumentRequest documentRequest = documentRequestService.fetchDocumentRequestById(documentRequestId);
        DocumentRemark updatedRemark = documentRemarkService.updateRemark(documentRemarkRequestDTO, documentRemarkId, documentRequest);
        return documentRemarkMapper.toDocumentRemarkResponseDTO(updatedRemark);
    }

    @Override
    public DocumentRemarkResponseDTO addRemark(Long documentRequestId,
                                               DocumentRemarkRequestDTO remarkRequestDTO,
                                               Authentication authentication) {
        // Fetch the document request
        DocumentRequest documentRequest = documentRequestService.fetchDocumentRequestById(documentRequestId);
        User user = userService.getUserFromAuthentication(authentication);
        DocumentRemark savedRemark = documentRemarkService.addRemark(documentRequest, remarkRequestDTO, user);
        return documentRemarkMapper.toDocumentRemarkResponseDTO(savedRemark);
    }

    @Override
    public void deleteRemark(Long documentRequestId, Long documentRemarkId) {


    }
}
