package com.teamupbe.user.profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    // from user
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phone;

    // from user profile
    private String bio;
    private String avatarUrl;

    // from user stats
    private Integer activitiesLocked;
    private Integer activitiesLateUnlocks;
    private Integer activitiesAttended;
    private Integer activitiesNoShows;
    private Integer activitiesOrganized;
    private Integer activitiesCancelled;
    private Double rating;
}
