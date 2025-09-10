package com.teamupbe.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;


    public RegisterResponse register(RegisterRequest request) {
        var username = request.getUsername();
        if (username == null || username.isBlank()) {
            throw new AuthenticationException(HttpStatus.BAD_REQUEST, "Username must not be empty.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new AuthenticationException(HttpStatus.BAD_REQUEST, "Password must not be empty.");
        }
        userRepository.findByUsername(username).ifPresent(u -> {
            throw new AuthenticationException(HttpStatus.BAD_REQUEST, "Username already in use.");
        });

        var user = UserEntity.builder()
                .username(username)
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);

        var token = jwtService.generateToken(request.getUsername());

        return RegisterResponse.builder()
                .id(user.getId())
                .token(token)
                .email(user.getEmail())
                .username(username)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );

        return new UserDetails(user);
    }

    public LoginResponse login(LoginRequest loginRequest, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            var token = jwtService.generateToken(loginRequest.getUsername());
            var userDetails = (UserDetails) authentication.getPrincipal();
            var user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                    () -> new AuthenticationException(HttpStatus.UNAUTHORIZED, "Invalid user request!")
            );
            return LoginResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .token(token)
                    .build();
        } else {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "Invalid user request!");
        }
    }

    @Transactional
    public UserResponse getUserByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(Object::toString).collect(Collectors.toList()))
                .build();
    }
}
