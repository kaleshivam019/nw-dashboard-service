package com.ey.nwdashboard.repository;

import com.ey.nwdashboard.entity.VacationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<VacationEntity, Long> {
    List<VacationEntity> findAllByvacationUserGPN(String vacationUserGPN);
}
