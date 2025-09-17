package com.teamupbe.user.profile;

import com.teamupbe.user.UserValidationException;
import com.teamupbe.user.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Clock;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final Clock clock;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileValidator userProfileValidator;


    public UserProfileEntity findByUser(UserEntity user) {
        return userProfileRepository.findByUser(user);
    }

    public UserProfileEntity create(UserEntity user) {
        var userProfile = UserProfileEntity.builder()
                .user(user)
                .build();
        return userProfileRepository.save(userProfile);
    }

    public UserProfileEntity updateBio(UserEntity user, String bio) {
        var userProfile = userProfileRepository.findByUser(user);
        userProfileValidator.validateBio(bio);
        userProfile.setBio(bio);
        return userProfileRepository.save(userProfile);
    }

    public UserProfileEntity updateAvatar(UserEntity user, MultipartFile avatarFile) {
        var userProfile = userProfileRepository.findByUser(user);
        userProfileValidator.validateAvatar(avatarFile);
        if (avatarFile != null && !avatarFile.isEmpty()) {
            var avatarUrl = saveAvatarFile(avatarFile, user.getId());
            userProfile.setAvatarUrl(avatarUrl);
        }
        return userProfileRepository.save(userProfile);
    }


    private String saveAvatarFile(MultipartFile file, Long userId) {
        try {
            var originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            var extension = "";
            int dot = originalFilename.lastIndexOf('.');
            if (dot >= 0) {
                extension = originalFilename.substring(dot);
            }

            var timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss")
                    .withZone(ZoneId.of("UTC"))
                    .format(clock.instant());
            var filename = "user-" + userId + "-" + timestamp + extension;

            var uploadDir = Path.of("uploads", "avatars");
            Files.createDirectories(uploadDir);
            var target = uploadDir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/avatars/" + filename;
        } catch (IOException e) {
            throw new UserValidationException("Failed to upload and store avatar file.");
        }
    }
}
