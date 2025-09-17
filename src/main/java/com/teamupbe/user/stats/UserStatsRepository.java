package com.teamupbe.user.stats;

import com.teamupbe.user.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatsRepository extends JpaRepository<UserStatsEntity, Long> {

    UserStatsEntity findByUser(UserEntity user);
}