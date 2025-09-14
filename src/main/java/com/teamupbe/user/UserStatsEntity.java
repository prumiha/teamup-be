package com.teamupbe.user;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "user_stats")
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsEntity {

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

    @Column(name = "activities_attended")
    private Integer activitiesAttended;

    @Column(name = "activities_aborted")
    private Integer activitiesAborted;

    @Column(name = "activities_organized")
    private Integer activitiesOrganized;

    @Column(name = "rating")
    private Double rating;

}