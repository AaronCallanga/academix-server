package com.academix.academix.document.request.service.impl;

import com.academix.academix.document.request.enums.DocumentStatus;
import com.academix.academix.document.request.repository.DocumentRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentRequestCleaningService {
    private final DocumentRequestRepository documentRequestRepository;

    @Transactional
    public void cleanUpRejectedAndExpiredRequest() {
        LocalDateTime threshold = LocalDateTime.now().minusYears(2);

        int deleted = documentRequestRepository
                .deleteByStatusInAndRequestDateBefore(
                        Set.of(DocumentStatus.REJECTED, DocumentStatus.EXPIRED),
                        threshold
                                                     );
        log.info("Deleted {} REJECTED/EXPIRED document requests older than {}", deleted, threshold);
    }

    @Transactional
    public void cleanUpReleasedRequest() {
        LocalDateTime threshold = LocalDateTime.now().minusNanos(3);

        int deleted = documentRequestRepository
                .deleteByStatusInAndRequestDateBefore(
                        Set.of(DocumentStatus.RELEASED),
                        threshold
                                                     );
        log.info("Deleted {} RELEASED document requests older than {}", deleted, threshold);
    }
}

