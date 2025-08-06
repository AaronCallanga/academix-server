package com.academix.academix.exception.types;

public class EmailSendFailureException extends RuntimeException {
    public EmailSendFailureException(String message, Exception ex) {
        super(message, ex);
    }

    public EmailSendFailureException(String message) {
        super(message);
    }
}
