package com.ey.nwdashboard.service;

import com.ey.nwdashboard.model.VacationResponse;

public interface VacationService {
    public VacationResponse getUserVacations(String userGPN);
}
