package com.teamupbe;

import com.teamupbe.activity.ActivityValidationException;
import com.teamupbe.security.authentication.AuthenticationException;
import com.teamupbe.user.UserValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public Map<String, String> authenticationException(RuntimeException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(UserValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> userValidationException(UserValidationException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(ActivityValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> activityValidationException(ActivityValidationException ex) {
        return Map.of("error", ex.getMessage());
    }
}
