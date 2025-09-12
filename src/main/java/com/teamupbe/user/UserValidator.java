package com.teamupbe.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9+\\- ]+$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]+$");

    private final UserRepository userRepository;

    public void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new UserValidationException("Username cannot be empty.");
        }

        if (username.length() < 3) {
            throw new UserValidationException("Username must be at least 3 characters long.");
        }
        if (username.length() > 20) {
            throw new UserValidationException("Username must not exceed 20 characters.");
        }

        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new UserValidationException("Username can only contain letters, numbers, '.', '-', and '_'.");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserValidationException("Username already in use.");
        }
    }

    public void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new UserValidationException("Password cannot be empty.");
        }

        if (password.length() < 3) {
            throw new UserValidationException("Password must be at least 3 characters long.");
        }
        if (password.length() > 20) {
            throw new UserValidationException("Password must not exceed 20 characters.");
        }
    }

    public void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            return; // optional field; no validation if empty
        }

        if (email.length() > 40) {
            throw new UserValidationException("Email must not exceed 40 characters.");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new UserValidationException("Invalid email format.");
        }
    }

    public void validatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            return; // optional field; no validation if empty
        }

        if (phone.length() > 20) {
            throw new UserValidationException("Phone number must not exceed 20 characters.");
        }

        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new UserValidationException("Phone number can only contain digits, '+', '-', and spaces.");
        }
    }
}
