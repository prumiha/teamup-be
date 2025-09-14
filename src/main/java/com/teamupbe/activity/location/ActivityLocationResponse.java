package com.teamupbe.activity.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLocationResponse {

    private String name;
    private String address;
    private Double locationLatitude;
    private Double locationLongitude;
}
