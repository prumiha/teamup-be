package com.teamupbe.security;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
}
