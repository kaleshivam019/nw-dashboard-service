package com.ey.nwdashboard.repository;

import com.ey.nwdashboard.entity.PublicHolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHolidayEntity, Long> {
    List<PublicHolidayEntity> findBypublicHolidayDate(LocalDate date);
}
