package com.teamupbe.activity.location;

import com.teamupbe.activity.ActivityValidationException;
import com.teamupbe.activity.activity.ActivityRepository;
import com.teamupbe.activity.activity.ActivityStatus;
import com.teamupbe.security.authentication.AuthenticationService;
import com.teamupbe.user.UserRepository;
import com.teamupbe.user.UserValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ActivityLocationValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9._\\- ]+$");
    private static final int ADDRESS_MAX_LENGTH = 50;

    private final ActivityLocationRepository activityLocationRepository;

    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ActivityValidationException("Location name cannot be empty.");
        }
        if (name.length() < 3) {
            throw new UserValidationException("Location name must be at least 3 characters long.");
        }
        if (name.length() > 20) {
            throw new UserValidationException("Location name must not exceed 20 characters.");
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new ActivityValidationException("Location name can only contain letters, numbers, '.', '-', and '_'.");
        }
        if (activityLocationRepository.findByName(name).isPresent()) {
            throw new ActivityValidationException("Location with that name already exists.");
        }
    }


    public void validateAddress(String address) {
        if (address == null || address.isBlank()) {
            return;
        }
        if (address.length() > ADDRESS_MAX_LENGTH) {
            throw new ActivityValidationException("Address must not exceed " + ADDRESS_MAX_LENGTH + " characters.");
        }
    }

    public void validateLatitude(Double latitude) {
        if (latitude == null) {
            return; // optional field
        }
        if (latitude < -90 || latitude > 90) {
            throw new ActivityValidationException("Latitude must be between -90 and 90 degrees.");
        }
    }

    public void validateLongitude(Double longitude) {
        if (longitude == null) {
            return; // optional field
        }
        if (longitude < -180 || longitude > 180) {
            throw new ActivityValidationException("Longitude must be between -180 and 180 degrees.");
        }
    }
}
