package com.teamupbe.user.user;

import com.teamupbe.security.authentication.RegisterRequest;
import com.teamupbe.security.role.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserValidator userValidator;


    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity update(UserEntity user, String fullName, String email, String phone) {
        userValidator.validateEmail(email);
        userValidator.validatePhone(phone);
        userValidator.validateFullName(fullName);

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    public UserEntity create(RegisterRequest request, Set<RoleEntity> roles){
        validateUser(request);

        var encodedPassword = encoder.encode(request.getPassword());
        var user = UserEntity.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
                .phone(request.getPhone())
                .fullName(request.getFullName())
                .roles(roles)
                .build();
        return userRepository.save(user);
    }

    private void validateUser(RegisterRequest request) {
        userValidator.validateUsername(request.getUsername());
        userValidator.validatePassword(request.getPassword());
        userValidator.validateEmail(request.getEmail());
        userValidator.validatePhone(request.getPhone());
        userValidator.validateFullName(request.getFullName());
    }

    public List<UserEntity> findByIds(List<Long> organizerIds) {
        return userRepository.findAllByIdIn(organizerIds);
    }
}