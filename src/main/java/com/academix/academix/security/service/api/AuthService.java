package com.academix.academix.security.service.api;

import com.academix.academix.security.dto.LoginRequestDTO;
import com.academix.academix.security.dto.LoginResponseDTO;
import com.academix.academix.security.dto.RegisterRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.io.UnsupportedEncodingException;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    String register(RegisterRequestDTO registerRequestDTO, String baseUrl) throws MessagingException, UnsupportedEncodingException;
    String verify(String token);
    String resendVerification(Authentication authentication, String baseUrl) throws MessagingException, UnsupportedEncodingException;
}
