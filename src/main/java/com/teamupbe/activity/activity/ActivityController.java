package com.teamupbe.activity.activity;

import com.teamupbe.activity.ActivityValidationException;
import com.teamupbe.activity.location.ActivityLocationEntity;
import com.teamupbe.security.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    private final ActivityControllerService activityControllerService;

    @GetMapping("/{id}")
    public ActivityResponse activity(@PathVariable Long id) {
        var activity = activityService.findById(id);
        return null;
    }

    @PostMapping("/create")
    public ActivityResponse create(@RequestBody ActivityCreateRequest request) {
        return activityControllerService.create(request);
    }

}