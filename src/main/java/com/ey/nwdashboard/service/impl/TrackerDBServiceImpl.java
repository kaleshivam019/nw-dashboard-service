package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.TrackerEntity;
import com.ey.nwdashboard.repository.TrackerRepository;
import com.ey.nwdashboard.service.TrackerDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackerDBServiceImpl implements TrackerDBService {

    @Autowired
    TrackerRepository trackerRepository;

    @Override
    public TrackerEntity getTrackerEntry(String trackerGPN) {
        if(null != trackerGPN && !trackerGPN.isBlank()){
            TrackerEntity trackerEntity = trackerRepository.findBytrackerUserGPN(trackerGPN);
            if(null != trackerEntity){
                return trackerEntity;
            }
        }
        return null;
    }

    @Override
    public TrackerEntity saveTrackerEntry(TrackerEntity trackerEntity) {
        if(null != trackerEntity){
            trackerEntity = trackerRepository.save(trackerEntity);

            if(null != trackerEntity){
                return trackerEntity;
            }
        }
        return null;
    }
}
