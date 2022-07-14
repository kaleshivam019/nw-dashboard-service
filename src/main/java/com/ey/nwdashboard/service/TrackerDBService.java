package com.ey.nwdashboard.service;

import com.ey.nwdashboard.entity.TrackerEntity;

public interface TrackerDBService {
    TrackerEntity getTrackerEntry(String trackerGPN);
}
