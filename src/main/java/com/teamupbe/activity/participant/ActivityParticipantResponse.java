package com.teamupbe.activity.participant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityParticipantResponse {

    private String username;
    private String fullName;
    private String status;
    private Integer passengers;
    private Integer maxPassengers;
    private Double reliability;
}
