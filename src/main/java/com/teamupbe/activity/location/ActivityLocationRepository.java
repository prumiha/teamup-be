package com.teamupbe.activity.location;

import com.teamupbe.activity.activity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityLocationRepository extends JpaRepository<ActivityLocationEntity, Long> {
    Optional<ActivityLocationEntity> findByName(String name);
}