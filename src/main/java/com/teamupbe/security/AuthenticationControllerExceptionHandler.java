package com.teamupbe.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = AuthenticationController.class)
public class AuthenticationControllerExceptionHandler {

    private final Clock clock;

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationControllerException(AuthenticationException ex) {
        var body = new LinkedHashMap<String, Object>();
        body.put("timestamp", clock.instant().toEpochMilli());
        body.put("error", ex.getStatus().name());
        body.put("message", ex.getMessage());

        return ResponseEntity.status(ex.getStatus()).body(body);
    }
}
