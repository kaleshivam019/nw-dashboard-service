package com.ey.nwdashboard.service;

import com.ey.nwdashboard.entity.VacationEntity;

import java.util.List;

public interface VacationDBService {
    List<VacationEntity> getVacations(String userGPN);

    VacationEntity insertOrUpdateVacation(VacationEntity vacationEntity);
}
