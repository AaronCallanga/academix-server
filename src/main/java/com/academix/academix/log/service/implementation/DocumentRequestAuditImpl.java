package com.academix.academix.log.service.implementation;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.log.dto.response.DocumentRequestAuditDetailResponseDTO;
import com.academix.academix.log.entity.DocumentRequestAudit;
import com.academix.academix.log.enums.ActorRole;
import com.academix.academix.log.enums.DocumentAction;
import com.academix.academix.log.mapper.DocumentRequestAuditMapper;
import com.academix.academix.log.repository.DocumentRequestAuditRepository;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
import com.academix.academix.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentRequestAuditImpl implements DocumentRequestAuditService {

    private final DocumentRequestAuditRepository documentRequestAuditRepository;
    private final DocumentRequestAuditMapper documentRequestAuditMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void logDocumentRequest(DocumentRequest documentRequest,
                                                              ActorRole actorRole,
                                                              DocumentAction documentAction,
                                                              String remark,
                                                              User userOptional) {
        if (!entityManager.contains(documentRequest)) {
            documentRequest = entityManager.merge(documentRequest);
        }
        DocumentRequestAudit audit = DocumentRequestAudit.builder()
                                                         // Snapshot from DocumentRequest
                                                         .requesterId(documentRequest.getRequestedBy().getId())
                                                         .requestedByName(documentRequest.getRequestedBy().getName()) // assuming you have getFullName()
                                                         .documentRequestId(documentRequest.getId())
                                                         .purpose(documentRequest.getPurpose())
                                                         .documentType(documentRequest.getDocumentType())
                                                         .status(documentRequest.getStatus())
                                                         .requestedAt(documentRequest.getRequestDate())
                                                         .pickUpDate(documentRequest.getPickUpDate())

                                                         // Actor info
                                                         .performedBy(userOptional) // null if SYSTEM
                                                         .performedAt(LocalDateTime.now())
                                                         .action(documentAction)
                                                         .actorRole(actorRole)
                                                         .remark(remark != null ? remark.trim() : null)
                                                         .build();


        DocumentRequestAudit savedAudit = documentRequestAuditRepository.save(audit);
        //return documentRequestAuditMapper.toDocumentRequestAuditResponseDTO(savedAudit);
    }

    @Override
    public Page<DocumentRequestAuditDetailResponseDTO> getAllDocumentRequestsByRequestId(Long documentRequestId, int page, int size, String sortDirection, String sortField) {
        // Build the PageRequest object
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<DocumentRequestAudit> documentRequestAuditList = documentRequestAuditRepository.findByDocumentRequestId(documentRequestId, pageRequest);
        List<DocumentRequestAuditDetailResponseDTO> documentRequestAuditDetailResponseDTOList = documentRequestAuditMapper.toDocumentRequestAuditResponseDTOList(documentRequestAuditList.getContent());
        return new PageImpl<>(documentRequestAuditDetailResponseDTOList, pageRequest, documentRequestAuditList.getTotalElements());
    }

}
