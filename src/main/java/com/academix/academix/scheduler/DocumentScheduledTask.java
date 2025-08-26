package com.academix.academix.scheduler;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.enums.DocumentStatus;
import com.academix.academix.document.request.repository.DocumentRequestRepository;
import com.academix.academix.email.api.DocumentEmailService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.print.Doc;
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


    public DocumentScheduledTask(DocumentEmailService documentEmailService,
                                 TaskScheduler documentScheduler,
                                 DocumentRequestRepository documentRequestRepository) {
        this.documentEmailService = documentEmailService;
        this.documentScheduler = documentScheduler;
        this.documentRequestRepository = documentRequestRepository;
    }

    @PostConstruct
    public void init() {
        documentScheduler.scheduleAtFixedRate(this::sendPickupReminders, Duration.ofDays(1));
        documentScheduler.scheduleAtFixedRate(this::expireOldRequest, Duration.ofDays(1));
        documentScheduler.scheduleAtFixedRate(this::cleanUpRejectedAndExpiredRequest, Duration.ofDays(30));
        documentScheduler.scheduleAtFixedRate(this::cleanUpReleasedRequest, Duration.ofDays(30));
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
        }
    }

    // Set request status to EXPIRED if not acted in 30 days
    private void expireOldRequest() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);

        List<DocumentRequest> oldRequests = documentRequestRepository.findByStatusAndRequestDate(DocumentStatus.REQUESTED, threshold);

        for (DocumentRequest request : oldRequests) {
            request.setStatus(DocumentStatus.EXPIRED);
        }

        documentRequestRepository.saveAll(oldRequests);
    }

    private void cleanUpRejectedAndExpiredRequest() {
        LocalDateTime threshold = LocalDateTime.now().minusYears(1);

        documentRequestRepository.deleteByStatusAndRequestDate(Set.of(DocumentStatus.REJECTED, DocumentStatus.EXPIRED), threshold);
    }

    private void cleanUpReleasedRequest() {
        LocalDateTime threshold = LocalDateTime.now().minusYears(3);

        documentRequestRepository.deleteByStatusAndRequestDate(Set.of(DocumentStatus.RELEASED), threshold);
    }




}
