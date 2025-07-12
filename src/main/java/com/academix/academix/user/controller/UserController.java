package com.academix.academix.user.controller;

import com.academix.academix.user.dto.UserDTO;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserDTO> getUsers() {
        User user = User.builder()
                        .id(1L)
                        .name("pogi")
                        .email("pogi@gmail.com")
                        .isVerified(true)
                        .build();

        return new ResponseEntity<>(userMapper.userToUserDTO(user), HttpStatus.OK);
    }
    @GetMapping("/url")
    public ResponseEntity<String> getUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return new ResponseEntity<>(url, HttpStatus.OK);
    }
}
// Entity - User, VerificationToken(one to one with user unidirectional rel)
// AuthService - register(UserRegistrationDTO, HttpServletRequest request)
//              check if email exist -> hashed password -> save db,
//              String randomCode = RandomString.make(64); or String token = UUID.randomUUID().toString();
//              create VerificationToken linked to user -> save db,
//              String url = request.getRquestUrl.toString().replace(request.getRequestURI(), "");
//              EmailService.sendVerificationEmail(url, token)
//      )
// Email Service - sendVerificationEmail(url, token
//                   create MimeMessage/SimpleMailMessage
//                   link = url + /api/v1/verify?token=...
//                   JavaMailSender.send(...)
//              )
//  Once user clicked -> GET url + /verify?token=...
// AuthController -> verify(@RequestParam token, HttpServletRequest request
//                          invoke authService.verify()
//                           )
// AuthService - verify(token
//                  VerificationTokenRepository.findToken if exist -> throw exception if not
//                  check if expired, find user then resendVerificationToken(user, request),delete token then return "token expired, check your email for new token"
//                  User user = verificationToken.getUser(); or User user = userRepo.findByVerificationToken
//                  user.setVerify(true) -> save db
//                  VerificationTokenRepository.deleteByToken(token)
//                  )
// AuthService - resendVerificationToken(user
//                   user.getEmail()
//                   Create Token
//                   url = request.getRequestUrl.toString().replace(request.getRequestURI(), "")
//                   emailService.sendVerificationEmail(url, token);
//                  )
// implement AOP to check if a user verify to all the request,
//                      get authentication.
//                      User user = repo.findUser(authentication.getName())
//                      check if verified
//                      if so, invoke the method, else throw exceptions Forbidden
// 	Add a scheduled task to delete tokens older than X hours
// Maybe create a token service, generateVerificationToken, CheckExpiration, ResetPassword, 2FA

// Sample AOP, uses Annotation
/*
@Around("@annotation(RequireVerified)")
public Object checkVerified(ProceedingJoinPoint joinPoint) throws Throwable {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String email = auth.getName(); // or getUsername
    User user = userRepo.findByEmail(email).orElseThrow();
    if (!user.isVerified()) {
        throw new ForbiddenException("Email not verified");
    }
    return joinPoint.proceed();
}
//Custom Annotation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireVerified {}

//Controller
@RequireVerified
@GetMapping("/secure-resource")
public ResponseEntity<?> getSecureInfo() { ... }

 */