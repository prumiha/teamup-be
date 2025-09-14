package com.teamupbe.activity.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLocationCreateRequest {

    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

}
