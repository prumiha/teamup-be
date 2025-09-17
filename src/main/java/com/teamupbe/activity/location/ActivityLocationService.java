package com.teamupbe.activity.location;

import com.teamupbe.activity.ActivityValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLocationService {

    private final ActivityLocationValidator activityLocationValidator;
    private final ActivityLocationRepository activityLocationRepository;


    public ActivityLocationEntity create(ActivityLocationCreateRequest request) {
        validateActivityLocation(request);

        var location = ActivityLocationEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
        return activityLocationRepository.save(location);
    }

    public ActivityLocationEntity findById(Long id) {
        return activityLocationRepository.findById(id).orElseThrow(
                () -> new ActivityValidationException("Activity location with id '" + id + "' does not exist.")
        );
    }

    private void validateActivityLocation(ActivityLocationCreateRequest request) {
        activityLocationValidator.validateName(request.getName());
        activityLocationValidator.validateAddress(request.getAddress());
        activityLocationValidator.validateLatitude(request.getLatitude());
        activityLocationValidator.validateLongitude(request.getLongitude());
    }


}