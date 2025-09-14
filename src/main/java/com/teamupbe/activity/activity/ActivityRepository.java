package com.teamupbe.activity.activity;

import com.teamupbe.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

    Optional<ActivityEntity> findByName(String name);

    long countByOrganizersContainingAndStatus(UserEntity user, ActivityStatus status);
}