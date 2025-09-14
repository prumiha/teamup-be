package com.teamupbe.activity.participant;

import com.teamupbe.activity.activity.ActivityEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityParticipantCreateRequest {

    private Long activityId;
    private Long userId;
    private Integer maxPassengers;
}
