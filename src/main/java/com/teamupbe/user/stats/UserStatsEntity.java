package com.teamupbe.user.stats;


import com.teamupbe.user.user.UserEntity;
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

    @Column(name = "activities_locked", nullable = false)
    private Integer activitiesLocked;

    @Column(name = "activities_late_unlocks", nullable = false)
    private Integer activitiesLateUnlocks;

    @Column(name = "activities_attended", nullable = false)
    private Integer activitiesAttended;

    @Column(name = "activities_no_shows", nullable = false)
    private Integer activitiesNoShows;

    @Column(name = "activities_organized", nullable = false)
    private Integer activitiesOrganized;

    @Column(name = "activities_cancelled", nullable = false)
    private Integer activitiesCancelled;

    @Column(name = "rating", nullable = true)
    private Double rating;

}