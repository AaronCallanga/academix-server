package com.academix.academix.exception.handler;

import com.academix.academix.exception.response.ErrorResponse;
import com.academix.academix.exception.response.ValidationErrorResponse;
import com.academix.academix.exception.types.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

}
