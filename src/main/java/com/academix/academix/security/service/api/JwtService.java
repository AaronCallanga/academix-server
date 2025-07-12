package com.academix.academix.security.service.api;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    public String generateToken(UserDetails userDetails);
}
