package com.academix.academix.security.service.implementation;

import com.academix.academix.security.dto.LoginRequestDTO;
import com.academix.academix.security.dto.LoginResponseDTO;
import com.academix.academix.security.dto.RegisterRequestDTO;
import com.academix.academix.security.entity.Role;
import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.security.repository.RoleRepository;
import com.academix.academix.security.repository.VerificationTokenRepository;
import com.academix.academix.security.service.api.AuthService;
import com.academix.academix.security.service.api.EmailService;
import com.academix.academix.security.service.api.JwtService;
import com.academix.academix.security.service.api.TokenService;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TokenService tokenService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // This triggers your UserDetailsService.loadUserByUsername()
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(), loginRequestDTO.getPassword()
                )
                                                                          );

        String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
        return new LoginResponseDTO(token);
    }

    @Transactional
    @Override
    public String register(RegisterRequestDTO registerRequestDTO, String baseUrl) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            return "Email Already Exists";
        }

        String hashedPassword = bCryptPasswordEncoder.encode(registerRequestDTO.getPassword());

        if (registerRequestDTO.getRoles() == null || registerRequestDTO.getRoles().isEmpty()) {
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_USER");
            registerRequestDTO.setRoles(roles);
        }

        Set<Role> roles = registerRequestDTO.getRoles().stream()
                                            .map(roleName -> roleRepository.findByName(roleName)
                                                                           .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                                            .collect(Collectors.toSet());

        User user = User.builder()
                .lrn(registerRequestDTO.getLrn())
                .name(registerRequestDTO.getName())
                .email(registerRequestDTO.getEmail())
                .password(hashedPassword)
                .contactNumber(registerRequestDTO.getContactNumber())
                .isVerified(false)
                .roles(roles)
                .build();

        userRepository.save(user);

        VerificationToken token = tokenService.generateToken(user);

        // send email
        emailService.sendEmail(user, baseUrl, token);
        return "User registered successfully";
    }

    @Transactional
    @Override
    public String verify(String token) {
        VerificationToken verificationToken = tokenService.getToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenService.deleteToken(verificationToken);
            throw new RuntimeException("Token Expired"); // throw new TokenExpiredException("Verification token has expired."); @Transactional(REQUIRES_NEW) tokenService.delete(token)
        }
        User user = verificationToken.getUser();
        user.setVerified(true);
        userRepository.save(user);

        tokenService.deleteToken(verificationToken);
        return "User verified successfully";
    }


}
