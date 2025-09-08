package com.academix.academix.security.controller;

import com.academix.academix.security.dto.LoginRequestDTO;
import com.academix.academix.security.dto.LoginResponseDTO;
import com.academix.academix.security.dto.RegisterRequestDTO;
import com.academix.academix.security.service.api.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return new ResponseEntity<>(authService.login(loginRequestDTO), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {     // return check your email
        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return new ResponseEntity<>(authService.register(registerRequestDTO, baseUrl), HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {
        return new ResponseEntity<>(authService.verify(token), HttpStatus.OK);
    }

    @PostMapping("/resend-token")
    public ResponseEntity<String> resendVerificationToken(Authentication authentication, HttpServletRequest request) {
        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return new ResponseEntity<>(authService.resendVerification(authentication, baseUrl), HttpStatus.OK);
    }

}
