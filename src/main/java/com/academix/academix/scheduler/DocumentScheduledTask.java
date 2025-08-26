package com.academix.academix.scheduler;

import com.academix.academix.email.api.DocumentEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DocumentScheduledTask {

    private final DocumentEmailService documentEmailService;
    private final TaskExecutor emailTaskExecutor;


}
