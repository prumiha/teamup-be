package com.teamupbe.activity.activity;

import com.teamupbe.activity.ActivityValidationException;
import com.teamupbe.security.authentication.AuthenticationService;
import com.teamupbe.user.UserValidationException;
import com.teamupbe.user.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ActivityValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9._\\- ]+$");
    private static final int DESCRIPTION_MAX_LENGTH = 500;
    public static final int MAX_PARTICIPANTS_LIMIT = 1024;
    public static final int MAX_ACTIVITIES_PER_USER = 3;
    public static final int ACTIVITY_NAME_MAX_LENGTH = 30;

    private final ActivityRepository activityRepository;
    private final Clock clock;
    private final AuthenticationService authenticationService;

    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ActivityValidationException("Name cannot be empty.");
        }
        if (name.length() < 3) {
            throw new UserValidationException("Name must be at least 3 characters long.");
        }
        if (name.length() > ACTIVITY_NAME_MAX_LENGTH) {
            throw new UserValidationException("Name must not exceed " + ACTIVITY_NAME_MAX_LENGTH + " characters.");
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new ActivityValidationException("Name can only contain letters, numbers, spaces, '.', '-', and '_'.");
        }
        if (activityRepository.findByName(name).isPresent()) {
            throw new ActivityValidationException("Activity with that name already exists.");
        }
    }


    public void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            return; // optional field; no validation if empty
        }
        if (description.length() > DESCRIPTION_MAX_LENGTH) {
            throw new ActivityValidationException("Description must not exceed " + DESCRIPTION_MAX_LENGTH + " characters.");
        }
    }

    public void validateType(String type) {
        if (type == null || type.isBlank()) {
            throw new ActivityValidationException("Activity type cannot be empty.");
        }

        if (type.length() > 20) {
            throw new UserValidationException("Activity type must not exceed 20 characters.");
        }
    }

    public void validatePrice(Double price) {
        if( price == null || price == 0) {
            return; // optional field; no validation if empty
        }
        if (price < 0) {
            throw new ActivityValidationException("Price must be greater than or equal to zero.");
        }
    }

    public void validateStartTime(Instant time) {
        if( time == null ) {
            throw new ActivityValidationException("Start time cannot be empty.");
        }
        if (time.isBefore(Instant.now(clock))) {
            throw new ActivityValidationException("Start time must be in the future.");
        }
    }

    public void validateEndTime(Instant endTime, Instant startTime) {
        if( endTime == null ) {
            return;
        }

        if (endTime.isBefore(startTime)) {
            throw new ActivityValidationException("Activity end time must be after start time.");
        }
    }

    public void validateMaxParticipants(Integer maxParticipants) {
        if( maxParticipants == null ) {
            return;
        }

        if (maxParticipants < 1) {
            throw new ActivityValidationException("Number of participants must be greater than zero.");
        }

        if( maxParticipants > MAX_PARTICIPANTS_LIMIT) {
            throw new ActivityValidationException("Number of participants must not exceed " + MAX_PARTICIPANTS_LIMIT + ".");
        }
    }

    public void validateOrganizers(List<UserEntity> organizers) {
        if( organizers == null || organizers.isEmpty() ) {
            throw new ActivityValidationException("Organizer cannot be empty.");
        }

        var userMakingCall = authenticationService.getUserMakingCall();

        if (organizers.stream().noneMatch(user -> userMakingCall.getId().equals(user.getId()))) {
            throw new ActivityValidationException("User creating activity must be an organizer.");
        }

        if (activityRepository.countByOrganizersContainingAndStatus(userMakingCall, ActivityStatus.SCHEDULED) > MAX_ACTIVITIES_PER_USER){
            throw new ActivityValidationException("User cannot create more than " + MAX_ACTIVITIES_PER_USER + " activities at a time.");
        }
    }
}
