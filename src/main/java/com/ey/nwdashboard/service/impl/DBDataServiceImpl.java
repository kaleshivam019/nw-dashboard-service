package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.model.DBDataRequestResponse;
import com.ey.nwdashboard.model.MessageModelResponse;
import com.ey.nwdashboard.repository.*;
import com.ey.nwdashboard.service.DBDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DBDataServiceImpl implements DBDataService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PublicHolidayRepository publicHolidayRepository;

    @Autowired
    private TrackerRepository trackerRepository;

    @Autowired
    private UserRegisteredRepository userRegisteredRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VacationRepository vacationRepository;

    @Override
    public ResponseEntity<DBDataRequestResponse> retrieveDBData() {
        DBDataRequestResponse dbDataRequestResponse = new DBDataRequestResponse();

        dbDataRequestResponse.setProjectEntityList(projectRepository.findAll());
        dbDataRequestResponse.setPublicHolidayEntityList(publicHolidayRepository.findAll());
        dbDataRequestResponse.setTrackerEntityList(trackerRepository.findAll());
        dbDataRequestResponse.setUserEntityList(userRepository.findAll());
        dbDataRequestResponse.setUserRegisteredEntityList(userRegisteredRepository.findAll());
        dbDataRequestResponse.setVacationEntityList(vacationRepository.findAll());

        if (null != dbDataRequestResponse)
            return new ResponseEntity<>(dbDataRequestResponse, HttpStatus.OK);
        return null;
    }

    @Override
    public ResponseEntity<MessageModelResponse> insertDBData(DBDataRequestResponse dbDataRequestResponse) {
        if(null != dbDataRequestResponse){
            MessageModelResponse messageModelResponse = new MessageModelResponse();
            try{
                projectRepository.saveAll(dbDataRequestResponse.getProjectEntityList());
                publicHolidayRepository.saveAll(dbDataRequestResponse.getPublicHolidayEntityList());
                trackerRepository.saveAll(dbDataRequestResponse.getTrackerEntityList());
                userRepository.saveAll(dbDataRequestResponse.getUserEntityList());
                userRegisteredRepository.saveAll(dbDataRequestResponse.getUserRegisteredEntityList());
                vacationRepository.saveAll(dbDataRequestResponse.getVacationEntityList());
                messageModelResponse.setMessage("Success");
                return new ResponseEntity<>(messageModelResponse, HttpStatus.CREATED);
            }catch (Exception exception){
                messageModelResponse.setMessage("Database failure!, Exception: " + exception.getMessage());
                return new ResponseEntity<>(messageModelResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return null;
    }
}
