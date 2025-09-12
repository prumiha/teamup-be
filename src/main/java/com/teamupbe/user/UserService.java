package com.teamupbe.user;

import com.teamupbe.security.UserResponse;
import com.teamupbe.security.authentication.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserValidator userValidator;


    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity create(RegisterRequest request){
        validateUser(request);

        var user = UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
        return userRepository.save(user);
    }

    private void validateUser(RegisterRequest request) {
        userValidator.validateUsername(request.getUsername());
        userValidator.validatePassword(request.getPassword());
        userValidator.validateEmail(request.getEmail());
        userValidator.validatePhone(request.getPhone());
    }
}