package com.teamupbe.user;

import com.teamupbe.user.profile.UserProfileEntity;
import com.teamupbe.user.profile.UserProfileResponse;
import com.teamupbe.user.profile.UserProfileService;
import com.teamupbe.user.profile.UserProfileUpdateRequest;
import com.teamupbe.user.stats.UserStatsEntity;
import com.teamupbe.user.stats.UserStatsService;
import com.teamupbe.user.user.UserEntity;
import com.teamupbe.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final UserStatsService userStatsService;

    @GetMapping("/profile/{username}")
    public UserProfileResponse profile(@PathVariable String username) {
        var user = userService.findByUsername(username)
                .orElseThrow(() -> new UserValidationException("Username does not exist."));
        var userProfile = userProfileService.findByUser(user);
        var userStats = userStatsService.findByUser(user);

        return mapToUserProfileResponse(user, userProfile, userStats);
    }

    @PostMapping(value = "/update")
    public UserProfileResponse updateProfile(@RequestBody UserProfileUpdateRequest request, Principal principal) {
        var username = principal.getName();
        var user = userService.findByUsername(username)
                .orElseThrow(() -> new UserValidationException("User does not exist."));
        user = userService.update(user, request.getFullName(), request.getEmail(), request.getPhone());
        var userProfile = userProfileService.updateBio(user, request.getBio());
        var userStats = userStatsService.findByUser(user);
        return mapToUserProfileResponse(user, userProfile, userStats);
    }

    @PostMapping(value = "/update-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserProfileResponse updateAvatar(@RequestPart(value = "avatarFile", required = false) org.springframework.web.multipart.MultipartFile avatarFile, Principal principal) {
        var username = principal.getName();
        var user = userService.findByUsername(username)
                .orElseThrow(() -> new UserValidationException("User does not exist."));
        var userProfile = userProfileService.updateAvatar(user, avatarFile);
        var userStats = userStatsService.findByUser(user);
        return mapToUserProfileResponse(user, userProfile, userStats);
    }


    private static UserProfileResponse mapToUserProfileResponse(UserEntity user, UserProfileEntity userProfile, UserStatsEntity userStats) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .bio(userProfile.getBio())
                .avatarUrl(userProfile.getAvatarUrl())
                .activitiesLocked(userStats.getActivitiesLocked())
                .activitiesLateUnlocks(userStats.getActivitiesLateUnlocks())
                .activitiesAttended(userStats.getActivitiesAttended())
                .activitiesNoShows(userStats.getActivitiesNoShows())
                .activitiesOrganized(userStats.getActivitiesOrganized())
                .activitiesCancelled(userStats.getActivitiesCancelled())
                .rating(userStats.getRating())
                .build();
    }
}