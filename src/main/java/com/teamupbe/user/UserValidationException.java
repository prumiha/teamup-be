package com.teamupbe.user;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}