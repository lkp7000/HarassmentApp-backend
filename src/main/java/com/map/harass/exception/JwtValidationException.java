package com.map.harass.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class JwtValidationException extends RuntimeException{
    private final HttpStatus status;
    private final String message;

    public JwtValidationException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;

    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
