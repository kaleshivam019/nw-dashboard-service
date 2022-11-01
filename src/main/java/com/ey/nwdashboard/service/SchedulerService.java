package com.ey.nwdashboard.service;

import com.ey.nwdashboard.model.MessageModelResponse;
import org.springframework.http.ResponseEntity;

public interface SchedulerService {
    ResponseEntity<MessageModelResponse> defaultingPendingVacationTrackerEntry();
}
