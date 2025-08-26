package com.academix.academix.scheduler;

import com.academix.academix.email.api.DocumentEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentScheduledTask {

    private final DocumentEmailService documentEmailService;
}
