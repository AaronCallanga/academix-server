package com.academix.academix.security.service.implementation;

import com.academix.academix.exception.types.ResourceNotFoundException;
import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.security.repository.VerificationTokenRepository;
import com.academix.academix.security.service.api.TokenService;
import com.academix.academix.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public VerificationToken generateToken(User user) {
        // 1. Delete expired tokens
        verificationTokenRepository.deleteByUserAndExpiryDateBefore(user, LocalDateTime.now());

        // 2. Check if there's still an unexpired token for the user, send it instead of creating new one
        Optional<VerificationToken> existingToken = verificationTokenRepository.findByUserAndExpiryDateAfter(user, LocalDateTime.now());
        if (existingToken.isPresent()) {
            System.out.println("Token still existing: " + existingToken.get().getToken());
            return existingToken.get();
        }

        // 3. Generate new token
        VerificationToken token = VerificationToken.builder()
                                .token(UUID.randomUUID().toString())
                                .expiryDate(LocalDateTime.now().plusMinutes(15))
                                .user(user)
                                .build();
        return verificationTokenRepository.save(token);
    }
    /*
    Always create a new transaction, even if the caller method is already running inside a transaction.
    Pause the outer transaction → Run this method in a new transaction → Commit/rollback it → Resume the outer one.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteToken(VerificationToken token) {
        verificationTokenRepository.delete(token);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public VerificationToken getToken(String token) {
        return verificationTokenRepository.findByToken(token)
                                   .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    }

    @Override
    @Transactional
    public int deleteExpiredTokens() {      // Use as a clean up in VerificaitonTokenTask scheduled
        return verificationTokenRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }
}
