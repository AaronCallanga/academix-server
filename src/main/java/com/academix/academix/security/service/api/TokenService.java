package com.academix.academix.security.service.api;

import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.user.entity.User;

import java.util.Optional;

public interface TokenService {
    VerificationToken generateToken(User user);
    void deleteToken(VerificationToken token);
    Optional<VerificationToken> getToken(String token);
}
