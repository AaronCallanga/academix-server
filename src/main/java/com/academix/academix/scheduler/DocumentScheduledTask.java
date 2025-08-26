package com.academix.academix.scheduler;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.enums.DocumentStatus;
import com.academix.academix.document.request.repository.DocumentRequestRepository;
import com.academix.academix.document.request.service.impl.DocumentRequestCleaningService;
import com.academix.academix.email.api.DocumentEmailService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class DocumentScheduledTask {

    private final DocumentEmailService documentEmailService;
    private final TaskScheduler documentScheduler;
    private final DocumentRequestRepository documentRequestRepository;
    private final DocumentRequestCleaningService documentRequestCleaningService;


    public DocumentScheduledTask(DocumentEmailService documentEmailService,
                                 TaskScheduler documentScheduler,
                                 DocumentRequestRepository documentRequestRepository,
                                 DocumentRequestCleaningService documentRequestCleaningService) {
        this.documentEmailService = documentEmailService;
        this.documentScheduler = documentScheduler;
        this.documentRequestRepository = documentRequestRepository;
        this.documentRequestCleaningService = documentRequestCleaningService;
    }

    @PostConstruct
    public void init() {
        documentScheduler.scheduleAtFixedRate(this::sendPickupReminders, Duration.ofDays(1)); // 1 day
        documentScheduler.scheduleAtFixedRate(this::expireOldRequest, Duration.ofDays(1));
        documentScheduler.scheduleAtFixedRate(this::cleanUpRejectedAndExpiredRequest, Duration.ofSeconds(10)); // 30 days
        documentScheduler.scheduleAtFixedRate(this::cleanUpReleasedRequest, Duration.ofSeconds(10));
    }

    // Send email reminder for request that is ready to pick up before 3 or 1 day of pick up
    private void sendPickupReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeDaysLater = now.plusDays(3).toLocalDate().atStartOfDay();
        LocalDateTime oneDayLater = now.plusDays(1).toLocalDate().atStartOfDay();

        List<DocumentRequest> requests = documentRequestRepository
                .findReadyForPickupByPickupDate(threeDaysLater, oneDayLater);

        for (DocumentRequest request : requests) {
            documentEmailService.sendReminder(request.getRequestedBy(), request);
            log.debug("Sending reminder for requestId={}, pickUpDate={}", request.getId(), request.getPickUpDate());
        }
    }

    // Set request status to EXPIRED if not acted in 30 days
    private void expireOldRequest() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);

        List<DocumentRequest> oldRequests = documentRequestRepository.findByStatusAndRequestDateBefore(DocumentStatus.REQUESTED, threshold);

        for (DocumentRequest request : oldRequests) {
            request.setStatus(DocumentStatus.EXPIRED);
            log.debug("Expiring requestId={} requestedAt={}", request.getId(), request.getRequestDate());
        }

        documentRequestRepository.saveAll(oldRequests);
    }

    public void cleanUpRejectedAndExpiredRequest() {
        documentRequestCleaningService.cleanUpRejectedAndExpiredRequest();
    }

    private void cleanUpReleasedRequest() {
        documentRequestCleaningService.cleanUpReleasedRequest();
    }




}
