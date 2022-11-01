package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.TrackerEntity;
import com.ey.nwdashboard.entity.UserEntity;
import com.ey.nwdashboard.model.MessageModelResponse;
import com.ey.nwdashboard.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    UserDBServiceImpl userDBService;

    @Autowired
    TrackerDBServiceImpl trackerDBService;

    /**
     * Service to make vacation tracker for each user to false every month's 1st day
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<MessageModelResponse> defaultingPendingVacationTrackerEntry() {
        MessageModelResponse messageModelResponse = new MessageModelResponse();
        try{
            //Get all the users
            List<UserEntity> existingUsers = userDBService.getAllUsers();
            if(null != existingUsers && !existingUsers.isEmpty()){
                existingUsers.stream().forEachOrdered(userEntity -> {
                    String userGPN = userEntity.getUserGPN();

                    TrackerEntity existingTrackerEntity = trackerDBService.getTrackerEntry(userGPN);

                    if(null != existingTrackerEntity){
                        //Setting the vacation tracker to false
                        existingTrackerEntity.setVacation(false);
                        existingTrackerEntity.setTrackerUpdatedBy("SYS-SCHEDULER");
                        existingTrackerEntity.setTrackerUpdatedOn(new Timestamp(System.currentTimeMillis()));

                        //Save/Update tracker entry
                        trackerDBService.saveTrackerEntry(existingTrackerEntity);
                    }
                });
                messageModelResponse.setMessage("Success");
                return new ResponseEntity<>(messageModelResponse, HttpStatus.OK);
            }
        } catch (Exception exception){
            messageModelResponse.setMessage("Failure: " + exception.getMessage());
            return new ResponseEntity<>(messageModelResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
