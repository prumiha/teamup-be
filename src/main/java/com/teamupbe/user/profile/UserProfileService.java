package com.teamupbe.user.profile;

import com.teamupbe.user.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;


    public UserProfileEntity findByUser(UserEntity user) {
        return userProfileRepository.findByUser(user);
    }

    public UserProfileEntity create(UserEntity user) {
        var userProfile = UserProfileEntity.builder()
                .user(user)
                .build();
        return userProfileRepository.save(userProfile);
    }
}
