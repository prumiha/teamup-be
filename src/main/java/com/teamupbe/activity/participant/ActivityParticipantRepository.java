package com.teamupbe.activity.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityParticipantRepository extends JpaRepository<ActivityParticipantEntity, Long> {
   
}