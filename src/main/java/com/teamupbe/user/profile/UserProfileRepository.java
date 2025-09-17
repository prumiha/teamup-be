package com.teamupbe.user.profile;

import com.teamupbe.user.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
    UserProfileEntity findByUser(UserEntity user);
}