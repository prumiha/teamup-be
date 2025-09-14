package com.teamupbe.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStatsRepository extends JpaRepository<UserStatsEntity, Long> {

    Optional<UserStatsEntity> findByUser(UserEntity user);
}