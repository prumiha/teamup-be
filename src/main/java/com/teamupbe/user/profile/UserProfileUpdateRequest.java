package com.teamupbe.user.profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileUpdateRequest {
    // update user
    private String fullName;
    private String email;
    private String phone;

    // update profile
    private String bio;
}
