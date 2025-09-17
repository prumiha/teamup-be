package com.teamupbe.user.profile;

import com.teamupbe.user.UserValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserProfileValidator {
    public static final List<String> ALLOWED_AVATAR_TYPES = List.of(
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/avif",
            "image/webp"
    );
    public static final List<String> ALLOWED_AVATAR_EXTENSIONS = List.of("jpg", "jpeg", "png", "webp", "avif");

    public void validateBio(String bio) {
        if (bio == null || bio.isBlank()) {
            return;
        }

        if (bio.length() > 500) {
            throw new UserValidationException("Bio must not exceed 200 characters.");
        }
    }

    public void validateAvatar(MultipartFile avatarFile){
        if(avatarFile == null || avatarFile.isEmpty()){
            return;
        }

        if(avatarFile.getSize() > 1024 * 1024){
            throw new UserValidationException("Avatar must not exceed 1MB.");
        }

        if (!ALLOWED_AVATAR_TYPES.contains(avatarFile.getContentType())) {
            throw new UserValidationException("Avatar must be one of following types: " + String.join(", ", ALLOWED_AVATAR_TYPES) + ".");
        }

        var filename = avatarFile.getOriginalFilename();
        if (filename != null) {
            String ext = FilenameUtils.getExtension(filename).toLowerCase();
            if (!ALLOWED_AVATAR_EXTENSIONS.contains(ext)) {
                throw new UserValidationException("Avatar file extension must be one of following: " + String.join(", ", ALLOWED_AVATAR_EXTENSIONS) + ".");
            }
        }
    }
}
