package com.teamupbe.activity.activity;

import com.teamupbe.activity.location.ActivityLocationResponse;
import com.teamupbe.activity.participant.ActivityParticipantResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResponse {

    private String name;
    private String description;
    private String type;
    private Double price;
    private Instant startTime;
    private Instant endTime;
    private Integer maxParticipants;
    private String status;
    private List<ActivityParticipantResponse> participants;
    private List<ActivityParticipantResponse> organizers;
    private ActivityLocationResponse location;
    
}
