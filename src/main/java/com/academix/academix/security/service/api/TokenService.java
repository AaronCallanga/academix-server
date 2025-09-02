package com.academix.academix.security.service.api;

import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.user.entity.User;

public interface TokenService {
    VerificationToken generateToken(User user);
    void deleteToken(VerificationToken token);
    VerificationToken getToken(String token);
    int deleteExpiredTokens();
}
