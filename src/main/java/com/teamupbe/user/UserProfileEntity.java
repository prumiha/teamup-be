package com.teamupbe.user;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "user_profiles")
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private UserEntity user;

    @Column(name = "avatar_url", nullable = true)
    private String avatarUrl;

    @Column(name = "bio", nullable = true)
    private String bio;
}