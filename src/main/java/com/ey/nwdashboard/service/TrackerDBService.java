package com.ey.nwdashboard.service;

import com.ey.nwdashboard.entity.TrackerEntity;

public interface TrackerDBService {
    TrackerEntity getTrackerEntry(String trackerGPN);
    TrackerEntity saveTrackerEntry(TrackerEntity trackerEntity);
    void deleteTrackerEntry(TrackerEntity trackerEntity);
}
