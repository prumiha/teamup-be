package com.teamupbe.user;

import com.teamupbe.user.profile.UserProfileResponse;
import com.teamupbe.user.profile.UserProfileService;
import com.teamupbe.user.stats.UserStatsService;
import com.teamupbe.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final UserStatsService userStatsService;

    @GetMapping("/{username}")
    public UserProfileResponse profile(@PathVariable String username) {
        var user = userService.findByUsername(username)
                .orElseThrow(() -> new UserValidationException("Username does not exist."));
        var userProfile = userProfileService.findByUser(user);
        var userStats = userStatsService.findByUser(user);

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