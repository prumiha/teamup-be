package com.teamupbe.activity.activity;

import com.teamupbe.activity.location.ActivityLocationCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityCreateRequest {

    private String name;
    private String description;
    private String type;
    private Double price;
    private Instant startTime;
    private Instant endTime;
    private Integer maxParticipants;
    private List<Long> organizerIds;

    // if location already exists
    private Long locationId;

    // if location does not exist
    private ActivityLocationCreateRequest createLocation;
}
