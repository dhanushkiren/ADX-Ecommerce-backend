package com.adverpix.ecommerce.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private HttpStatus status;
    private int statusCode;

    public ErrorResponse(String message, HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
        this.statusCode = status.value();
    }
}
