package com.academix.academix.security.service.implementation;

import com.academix.academix.exception.types.BadRequestException;
import com.academix.academix.exception.types.ConflictException;
import com.academix.academix.exception.types.ResourceNotFoundException;
import com.academix.academix.exception.types.TokenExpiredException;
import com.academix.academix.security.dto.LoginRequestDTO;
import com.academix.academix.security.dto.LoginResponseDTO;
import com.academix.academix.security.dto.RegisterRequestDTO;
import com.academix.academix.security.entity.Role;
import com.academix.academix.security.entity.VerificationToken;
import com.academix.academix.security.repository.RoleRepository;
import com.academix.academix.security.service.api.AuthService;
import com.academix.academix.email.api.AuthEmailService;
import com.academix.academix.security.service.api.JwtService;
import com.academix.academix.security.service.api.TokenService;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AuthEmailService authEmailService;
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public String register(RegisterRequestDTO registerRequestDTO, String baseUrl){
        if (userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            throw new ConflictException("User already exist!");
        }

        String hashedPassword = bCryptPasswordEncoder.encode(registerRequestDTO.getPassword());

        if (registerRequestDTO.getRoles() == null || registerRequestDTO.getRoles().isEmpty()) {
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_USER");
            registerRequestDTO.setRoles(roles);
        }

        Set<Role> roles = registerRequestDTO.getRoles().stream()
                                            .map(roleName -> roleRepository.findByName(roleName)
                                                                           .orElseThrow(() -> new BadRequestException("Role not found: " + roleName)))
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
        authEmailService.sendVerification(user, baseUrl, token);
        return "User registered successfully";
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public String verify(String token) {
        VerificationToken verificationToken = tokenService.getToken(token);

        // Check if token expired
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenService.deleteToken(verificationToken);
            System.out.println("Token Expired");
            throw new TokenExpiredException("Verification token has expired."); //Note: @Transactional(REQUIRES_NEW) tokenService.delete(token)
        }
        User user = verificationToken.getUser();        //maybe find user by token,
        user.setVerified(true);
        userRepository.save(user);

        tokenService.deleteToken(verificationToken);
        return "User verified successfully";
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override   // only for logged in
    public String resendVerification(Authentication authentication, String baseUrl) {
        String email;
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            email = jwt.getClaim("sub"); // or "email", depending on your JWT structure
        } else {
            throw new IllegalStateException("Unexpected authentication principal type");
        }

        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        VerificationToken verificationToken = tokenService.generateToken(user);

        authEmailService.sendVerification(user, baseUrl, verificationToken);
        return "Token send successfully. Please check your email";
    }


}
