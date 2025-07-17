package com.academix.academix.security.repository;

import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByUserAndExpiryDateAfter(User user, LocalDateTime now);
    void deleteByUserAndExpiryDateBefore(User user, LocalDateTime now);
    void deleteByExpiryDateBefore(LocalDateTime now);
}
