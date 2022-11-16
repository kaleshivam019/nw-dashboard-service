package com.ey.nwdashboard.repository;

import com.ey.nwdashboard.entity.UserRegisteredEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegisteredRepository extends JpaRepository<UserRegisteredEntity, Long> {
    UserRegisteredEntity findByuserName(String userName);
}
