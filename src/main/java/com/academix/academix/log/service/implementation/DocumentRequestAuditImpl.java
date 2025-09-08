package com.academix.academix.log.service.implementation;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.exception.types.BadRequestException;
import com.academix.academix.exception.types.ResourceNotFoundException;
import com.academix.academix.log.dto.response.DocumentRequestAuditDetailResponseDTO;
import com.academix.academix.log.dto.response.DocumentRequestAuditListResponseDTO;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentRequestAuditImpl implements DocumentRequestAuditService {

    private final DocumentRequestAuditRepository documentRequestAuditRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Async("logExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
                                                         .performedById(userOptional.getId()) // null if SYSTEM
                                                         .performedByName(userOptional.getName())
                                                         .performedAt(LocalDateTime.now())
                                                         .action(documentAction)
                                                         .actorRole(actorRole)
                                                         .remark(remark != null ? remark.trim() : null)
                                                         .build();


        DocumentRequestAudit savedAudit = documentRequestAuditRepository.save(audit);
        //return documentRequestAuditMapper.toDocumentRequestAuditResponseDTO(savedAudit);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DocumentRequestAudit> getAllDocumentRequestsByRequestId(Long documentRequestId, PageRequest pageRequest) {
        return documentRequestAuditRepository.findByDocumentRequestId(documentRequestId, pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public DocumentRequestAudit getDocumentRequestAuditDetails(DocumentRequest documentRequest, Long auditId) {
        DocumentRequestAudit documentRequestAudit = documentRequestAuditRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException("Document request audit not found with the id: " + auditId));

        if (!documentRequest.getId().equals(documentRequestAudit.getDocumentRequestId())) {
            throw new BadRequestException("Audit ID " + auditId + " does not belong to Document Request ID " + documentRequest.getId());
        }

        return documentRequestAudit;
    }

}
