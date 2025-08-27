package com.academix.academix.scheduler;

import com.academix.academix.security.service.api.TokenService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Slf4j
//@RequiredArgsConstructor
public class VerificationTokenScheduledTask {

    private final TaskScheduler tokenScheduler;
    private final TokenService tokenService;

    @Autowired
    public VerificationTokenScheduledTask(@Qualifier("tokenScheduler") TaskScheduler tokenScheduler,
                                          TokenService tokenService
                                         ) {
        this.tokenScheduler = tokenScheduler;
        this.tokenService = tokenService;
    }

    @PostConstruct
    public void init() {
        tokenScheduler.scheduleAtFixedRate(this::deleteAllExpiredTokens, Duration.ofHours(1));
    }

    private void deleteAllExpiredTokens() {
        log.info("Running token cleanup at {}", LocalDateTime.now());
        int deleted = tokenService.deleteExpiredTokens();
        log.info("Cleaning up expired tokens on thread {}", Thread.currentThread().getName());     // For testing
        log.info("Deleted {} expired verification tokens", deleted);
    }

}
