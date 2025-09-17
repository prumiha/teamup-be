package com.teamupbe.activity.activity;

import com.teamupbe.activity.ActivityValidationException;
import com.teamupbe.activity.location.ActivityLocationEntity;
import com.teamupbe.activity.location.ActivityLocationResponse;
import com.teamupbe.activity.location.ActivityLocationService;
import com.teamupbe.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    private final UserService userService;
    private final ActivityLocationService activityLocationService;

    @GetMapping("/{id}")
    public ActivityResponse activity(@PathVariable Long id) {
        var activity = activityService.findById(id);
        return null;
    }

    @PostMapping("/create")
    public ActivityResponse create(@RequestBody ActivityCreateRequest request) {
        return createActivity(request);
    }

    private ActivityResponse createActivity(ActivityCreateRequest request) {
        var location = getOrCreateLocation(request);
        var organizers = userService.findByIds(request.getOrganizerIds());
        var activity = activityService.create(request, location, organizers);

        var responseLocation = ActivityLocationResponse.builder()
                .name(location.getName())
                .address(location.getAddress())
                .locationLongitude(location.getLongitude())
                .locationLatitude(location.getLatitude())
                .build();

        return ActivityResponse.builder()
                .name(activity.getName())
                .description(activity.getDescription())
                .type(activity.getType())
                .price(activity.getPrice())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .maxParticipants(activity.getMaxParticipants())
                .status(activity.getStatus().toString())
                .participants(List.of())
                .location(responseLocation)
                .build();
    }

    private ActivityLocationEntity getOrCreateLocation(ActivityCreateRequest request) {
        ActivityLocationEntity activityLocationEntity;
        if (request.getLocationId() != null) {
            activityLocationEntity = activityLocationService.findById(request.getLocationId());
        } else if (request.getCreateLocation() != null) {
            activityLocationEntity = activityLocationService.create(request.getCreateLocation());
        } else {
            throw new ActivityValidationException("Location must be provided.");
        }
        return activityLocationEntity;
    }
}