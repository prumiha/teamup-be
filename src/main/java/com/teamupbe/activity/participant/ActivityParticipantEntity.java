package com.teamupbe.activity.participant;


import com.teamupbe.activity.activity.ActivityEntity;
import com.teamupbe.user.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "activity_participants")
@NoArgsConstructor
@AllArgsConstructor
public class ActivityParticipantEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private ActivityEntity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParticipationStatus status;

    @Column(name = "max_passengers", nullable = true)
    private Integer maxPassengers;

    @ManyToMany
    @JoinTable(
            name = "activity_driver_passengers",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
    private List<UserEntity> passengers = new ArrayList<>();
}