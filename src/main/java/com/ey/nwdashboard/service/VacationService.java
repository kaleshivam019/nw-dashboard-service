package com.ey.nwdashboard.service;

import com.ey.nwdashboard.model.VacationRequest;
import com.ey.nwdashboard.model.VacationResponse;
import org.springframework.http.ResponseEntity;

public interface VacationService {
    VacationResponse getUserVacations(String userGPN);
    ResponseEntity saveUserVacations(VacationRequest vacationRequest);
}
