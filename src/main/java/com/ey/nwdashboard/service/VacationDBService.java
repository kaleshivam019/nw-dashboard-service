package com.ey.nwdashboard.service;

import com.ey.nwdashboard.entity.PublicHolidayEntity;
import com.ey.nwdashboard.entity.VacationEntity;

import java.time.LocalDate;
import java.util.List;

public interface VacationDBService {
    List<VacationEntity> getVacations(String userGPN);

    VacationEntity insertOrUpdateVacation(VacationEntity vacationEntity);

    PublicHolidayEntity insertOrUpdatePublicHoliday(PublicHolidayEntity publicHolidayEntity);

    List<PublicHolidayEntity> getPublicHolidays(LocalDate date);

    void deleteVacations(List<VacationEntity> existingVacations);
}
