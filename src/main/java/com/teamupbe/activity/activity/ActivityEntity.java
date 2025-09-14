package com.teamupbe.activity.activity;


import com.teamupbe.activity.location.ActivityLocationEntity;
import com.teamupbe.activity.participant.ActivityParticipantEntity;
import com.teamupbe.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(
        name = "activities",
        indexes = {@Index(name = "idx_activity_status", columnList = "status")}
)
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "price", nullable = true)
    private Double price;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time", nullable = true)
    private Instant endTime;

    @Column(name = "max_participants", nullable = true)
    private Integer maxParticipants;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ActivityStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = true)
    private ActivityLocationEntity location;

    @ManyToMany
    @JoinTable(name = "activity_participants",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_participant_id"))
    private List<ActivityParticipantEntity> activityParticipants = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "activity_organizers",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> organizers = new ArrayList<>();

}