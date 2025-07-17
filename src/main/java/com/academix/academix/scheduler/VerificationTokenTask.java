package com.academix.academix.scheduler;

import com.academix.academix.security.repository.VerificationTokenRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Slf4j
//@RequiredArgsConstructor
public class VerificationTokenTask {

    private final TaskScheduler tokenScheduler;
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenTask(@Qualifier("tokenScheduler") TaskScheduler tokenScheduler,
                          VerificationTokenRepository verificationTokenRepository) {
        this.tokenScheduler = tokenScheduler;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @PostConstruct
    public void init() {
        tokenScheduler.scheduleAtFixedRate(this::deleteAllExpiredTokens, Duration.ofHours(1));
    }

    public void deleteAllExpiredTokens() {
        log.info("Running token cleanup at {}", LocalDateTime.now());
        int deleted = verificationTokenRepository.deleteByExpiryDateBefore(LocalDateTime.now());
        log.info("Cleaning up expired tokens on thread {}", Thread.currentThread().getName());     // For testing
        log.info("Deleted {} expired verification tokens", 0);
    }

}
