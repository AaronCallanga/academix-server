package com.academix.academix.exception.handler;

import com.academix.academix.exception.response.ErrorResponse;
import com.academix.academix.exception.types.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errorResponse("RESOURCE NOT FOUND")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse.toString(), HttpStatus.NOT_FOUND);
    }


}
