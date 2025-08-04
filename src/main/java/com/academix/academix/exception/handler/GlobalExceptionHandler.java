package com.academix.academix.exception.handler;

import com.academix.academix.exception.response.ErrorResponse;
import com.academix.academix.exception.response.ValidationErrorResponse;
import com.academix.academix.exception.types.BadRequestException;
import com.academix.academix.exception.types.ConflictException;
import com.academix.academix.exception.types.EmailSendFailureException;
import com.academix.academix.exception.types.ResourceNotFoundException;
import com.academix.academix.exception.types.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles custom 404 errors when a resource is not found.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errorResponse("RESOURCE NOT FOUND")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Request cannot be completed due to a conflict with the current state of the resource
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                e.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Client sends invalid or malformed data
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                e.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Client sends invalid or expired verification token
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException e) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "TOKEN_EXPIRED",
                e.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Client sends invalid or expired verification token
    @ExceptionHandler(EmailSendFailureException.class)
    public ResponseEntity<ErrorResponse> handleEmailSendFailureException(EmailSendFailureException e) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "SENDING_EMAIL_FAILED",
                e.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors from @Valid and Bean Validation API.
     * Returns field-specific validation messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e
                .getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors
                        .put(error.getField(), error.getDefaultMessage())
                        );
        ValidationErrorResponse validationErrorResponse = ValidationErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .statusMessage("VALIDATION ERROR")
                .errors(errors)
                .build();
        return new ResponseEntity<>(validationErrorResponse.toString(), HttpStatus.BAD_REQUEST);
    }

    // Authenticated user lacks necessary roles/permissions.
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "FORBIDDEN",
                e.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // User is not yet authenticated
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "UNAUTHORIZED",
                e.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // Client's request used wrong HTTP method (e.g., POST instead of GET).
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "REQUEST_METHOD_NOT_ALLOWED",
                e.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // Handles all uncaught exceptions — 500 Internal Server Error.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                e.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
