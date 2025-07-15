package com.academix.academix.security.service.implementation;

import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.security.repository.VerificationTokenRepository;
import com.academix.academix.security.service.api.TokenService;
import com.academix.academix.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken generateToken(User user) {
        return VerificationToken.builder()
                                .token(UUID.randomUUID().toString())
                                .expiryDate(LocalDateTime.now().plusMinutes(15))
                                .user(user)
                                .build();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteToken(VerificationToken token) {
        verificationTokenRepository.delete(token);
    }
}
