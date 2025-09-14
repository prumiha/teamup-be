package com.teamupbe.activity.participant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityParticipantService {
    private final ActivityParticipantRepository activityParticipantRepository;

    public ActivityParticipantEntity create(ActivityParticipantCreateRequest request){
        return null;
    }
}
