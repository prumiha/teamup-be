package com.teamupbe.security;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends RuntimeException {
    private final HttpStatus status;

    public AuthenticationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
