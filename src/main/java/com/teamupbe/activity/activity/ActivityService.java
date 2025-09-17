package com.teamupbe.activity.activity;

import com.teamupbe.activity.ActivityValidationException;
import com.teamupbe.activity.location.ActivityLocationEntity;
import com.teamupbe.user.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.teamupbe.activity.activity.ActivityStatus.SCHEDULED;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityValidator activityValidator;


    public ActivityEntity create(
            ActivityCreateRequest request,
            ActivityLocationEntity location,
            List<UserEntity> organizers
    ) {

        validateActivity(request, organizers);

        var activity = ActivityEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .price(request.getPrice())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .maxParticipants(request.getMaxParticipants())
                .status(SCHEDULED)
                .location(location)
                .organizers(organizers)
                .build();
        return activityRepository.save(activity);
    }

    private void validateActivity(ActivityCreateRequest request, List<UserEntity> organizers) {
        activityValidator.validateName(request.getName());
        activityValidator.validateDescription(request.getDescription());
        activityValidator.validateType(request.getType());
        activityValidator.validatePrice(request.getPrice());
        activityValidator.validateStartTime(request.getStartTime());
        activityValidator.validateEndTime(request.getEndTime(), request.getStartTime());
        activityValidator.validateMaxParticipants(request.getMaxParticipants());
        activityValidator.validateOrganizers(organizers);

    }


    public ActivityEntity findById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new ActivityValidationException("You are trying to access activity that does not exist."));
    }

    public static ActivityResponse mapActivityEntityToActivityResponse(ActivityEntity entity) {
        return ActivityResponse.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .type(entity.getType())
                .price(entity.getPrice())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .maxParticipants(entity.getMaxParticipants())
                .status(entity.getStatus().toString())
                .build();
    }
}