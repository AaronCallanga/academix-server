package com.academix.academix.user.service.api;

import com.academix.academix.user.entity.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    User getUUserFromAuthentication(Authentication authentication);
}
