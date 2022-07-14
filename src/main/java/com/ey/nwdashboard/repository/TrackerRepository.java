package com.ey.nwdashboard.repository;

import com.ey.nwdashboard.entity.TrackerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackerRepository extends JpaRepository<TrackerEntity, Long> {
    TrackerEntity findBytrackerUserGPN(String trackerUserGPN);
}
