package com.teamupbe.user.stats;

import com.teamupbe.user.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStatsService {
    private final UserStatsRepository userStatsRepository;


    public UserStatsEntity findByUser(UserEntity user) {
        return userStatsRepository.findByUser(user);
    }

    public UserStatsEntity create(UserEntity user) {
        var userStats = UserStatsEntity.builder()
                .user(user)
                .activitiesLocked(0)
                .activitiesLateUnlocks(0)
                .activitiesAttended(0)
                .activitiesNoShows(0)
                .activitiesOrganized(0)
                .activitiesCancelled(0)
                .build();

        return userStatsRepository.save(userStats);
    }
}
