package com.academix.academix.scheduler;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.repository.DocumentRequestRepository;
import com.academix.academix.email.api.DocumentEmailService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

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
    }

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



}
