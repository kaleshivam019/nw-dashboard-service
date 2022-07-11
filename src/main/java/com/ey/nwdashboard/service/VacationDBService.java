package com.ey.nwdashboard.service;

import com.ey.nwdashboard.entity.VacationEntity;

import java.util.List;

public interface VacationDBService {
    public List<VacationEntity> getVacations(String userGPN);
}
