package com.teamupbe.security.authentication;

import com.teamupbe.security.JwtService;
import com.teamupbe.security.UserDetails;
import com.teamupbe.user.UserEntity;
import com.teamupbe.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final JwtService jwtService;
    private final UserService userService;


    public RegisterResponse register(RegisterRequest request) {
        var username = request.getUsername();
        var user = userService.create(request);
        var token = jwtService.generateToken(request.getUsername());

        return RegisterResponse.builder()
                .id(user.getId())
                .token(token)
                .username(username)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );

        return new UserDetails(user);
    }

    public LoginResponse login(LoginRequest loginRequest, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            var token = jwtService.generateToken(loginRequest.getUsername());
            var userDetails = (UserDetails) authentication.getPrincipal();
            var user = userService.findByUsername(userDetails.getUsername()).orElseThrow(
                    () -> new AuthenticationException("Invalid user request!")
            );
            return LoginResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .token(token)
                    .build();
        } else {
            throw new AuthenticationException("Invalid user request!");
        }
    }

    public UserEntity getUserMakingCall() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}
