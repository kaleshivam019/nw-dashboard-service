package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.PublicHolidayEntity;
import com.ey.nwdashboard.entity.TrackerEntity;
import com.ey.nwdashboard.entity.VacationEntity;
import com.ey.nwdashboard.model.*;
import com.ey.nwdashboard.service.TrackerDBService;
import com.ey.nwdashboard.service.UserDBService;
import com.ey.nwdashboard.service.VacationDBService;
import com.ey.nwdashboard.service.VacationService;
import com.ey.nwdashboard.utils.DashboardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class VacationServiceImpl implements VacationService {

    @Autowired
    UserDBService userDBService;

    @Autowired
    VacationDBService vacationDBService;

    @Autowired
    TrackerDBService trackerDBService;

    /**
     * This method is responsible to fetch all the vacations of a user
     * @param userGPN
     * @return
     */
    @Override
    public VacationResponse getUserVacations(String userGPN) {
        VacationResponse vacationResponse = new VacationResponse();

        if(userDBService.isExistingUser(userGPN)){
            List<VacationEntity> vacationEntityList = vacationDBService.getVacations(userGPN);
            if(null != vacationEntityList && !vacationEntityList.isEmpty()){
                List<VacationModel> vacationModelList = DashboardUtils.convertVacationEntityToModel(vacationEntityList);

                if(null != vacationModelList && !vacationModelList.isEmpty()){
                    vacationResponse.setVacations(vacationModelList);
                }
            }
        }
        return vacationResponse;
    }

    /**
     * This method will save the vacations of the user
     * @param vacationRequest
     * @return
     */
    @Override
    public ResponseEntity saveUserVacations(VacationRequest vacationRequest) {
        if(null != vacationRequest &&
                null != vacationRequest.getUserGPN() &&
                userDBService.isExistingUser(vacationRequest.getUserGPN()) &&
                (null != vacationRequest.getVacations() && !vacationRequest.getVacations().isEmpty())){
            String userGPN = vacationRequest.getUserGPN();
            List<VacationModel> newVacations = vacationRequest.getVacations();
            List<VacationEntity> existingVacations = vacationDBService.getVacations(userGPN);

            AtomicInteger createUpdateCounter = new AtomicInteger();

            newVacations.stream().forEach(vacationModel -> {
                String vacationDate = vacationModel.getVacationDate();
                boolean isVacationPlanned = vacationModel.isVacationPlanned();
                boolean isVacationFullDay = vacationModel.isVacationFullDay();
                boolean isPublicHoliday = vacationModel.isPublicHoliday();

                VacationEntity existingVacation = null;
                if(null != existingVacations){
                    existingVacation = existingVacations.stream().filter(vacationEntity -> vacationEntity.getVacationDate().toString().equals(vacationDate)).findFirst().orElse(null);
                }

                if(null != existingVacation){
                    //Vacation is already present so update operation is required

                    existingVacation.setVacationFullDay(isVacationFullDay);
                    existingVacation.setVacationPlanned(isVacationPlanned);
                    existingVacation.setPublicHoliday(isPublicHoliday);
                    existingVacation.setVacationUpdatedBy(userGPN);
                    existingVacation.setVacationUpdatedOn(new Timestamp(System.currentTimeMillis()));

                    vacationDBService.insertOrUpdateVacation(existingVacation);
                    createUpdateCounter.getAndIncrement();
                }else{
                    //Vacation is not present so insert operation is required
                    VacationEntity vacationEntity = new VacationEntity();

                    //Formatting date from String to Date type
                    Date vacationDateFormatted = new Date();
                    try{
                        vacationDateFormatted = new SimpleDateFormat("yyyy-MM-dd").parse(vacationDate);
                    }catch (ParseException e){
                        e.printStackTrace();
                    }

                    vacationEntity.setVacationDate(vacationDateFormatted);
                    vacationEntity.setVacationPlanned(isVacationPlanned);
                    vacationEntity.setVacationFullDay(isVacationFullDay);
                    vacationEntity.setPublicHoliday(isPublicHoliday);
                    vacationEntity.setVacationUserGPN(userGPN);
                    vacationEntity.setVacationCreatedBy(userGPN);
                    vacationEntity.setVacationCreatedOn(new Timestamp(System.currentTimeMillis()));
                    vacationEntity.setVacationUpdatedBy(userGPN);
                    vacationEntity.setVacationUpdatedOn(new Timestamp(System.currentTimeMillis()));

                    //Make tracker entry for each user if not found in vacation tracker table - START

                    //Get the tracker entry for GPN
                    TrackerEntity trackerEntry = trackerDBService.getTrackerEntry(userGPN);

                    //If tracker entry not found for user then insert a new one time entry
                    if(null == trackerEntry){
                        TrackerEntity newTrackerEntity = new TrackerEntity();
                        newTrackerEntity.setTrackerUserGPN(userGPN);
                        newTrackerEntity.setVacation(true);
                        newTrackerEntity.setAllowance(false);
                        newTrackerEntity.setShift(false);
                        newTrackerEntity.setTrackerCreatedBy("SYS-ADMIN");
                        newTrackerEntity.setTrackerCreatedOn(new Timestamp(System.currentTimeMillis()));
                        newTrackerEntity.setTrackerUpdatedBy("SYS-ADMIN");
                        newTrackerEntity.setTrackerUpdatedOn(new Timestamp(System.currentTimeMillis()));

                        trackerDBService.saveTrackerEntry(newTrackerEntity);
                    }else if(null != trackerEntry && !trackerEntry.isVacation()){ //If tracker entry is found but vacation flag is not true then set it true and update the entry
                        //update the existing entry for the user

                        trackerEntry.setVacation(true);
                        trackerEntry.setTrackerUpdatedBy("SYS-ADMIN");
                        trackerEntry.setTrackerUpdatedOn(new Timestamp(System.currentTimeMillis()));

                        trackerDBService.saveTrackerEntry(trackerEntry);
                    }

                    //END

                    vacationDBService.insertOrUpdateVacation(vacationEntity);
                    createUpdateCounter.getAndIncrement();
                }
            });

            if(createUpdateCounter.get() == 0){
                return ResponseEntity.ok("No vacations to be updated");
            }else{
                return ResponseEntity.ok(createUpdateCounter.get() + " Vacations successfully created or updated");
            }

        }
        return null;
    }

    @Override
    public ResponseEntity savePublicHolidays(PublicHolidayRequest publicHolidayRequest) {
        if(null != publicHolidayRequest &&
                null != publicHolidayRequest.getPublicHolidays() &&
                !publicHolidayRequest.getPublicHolidays().isEmpty()){

            AtomicInteger insertUpdateCount = new AtomicInteger();

            publicHolidayRequest.getPublicHolidays().stream().forEach(publicHolidayModel -> {
                //convert date to local date for each holiday
                LocalDate holidayDate = LocalDate.parse(publicHolidayModel.getDate());

                //Check if holiday already present in db
                List<PublicHolidayEntity> existingPublicHolidayList = vacationDBService.getPublicHolidays(holidayDate);

                if(null != existingPublicHolidayList){
                    //add existing stored location for public holiday in set
                    Set<String> exitingLocations = new HashSet<>();
                    existingPublicHolidayList.forEach(publicHolidayEntity -> exitingLocations.add(publicHolidayEntity.getLocation()));

                    if(!exitingLocations.contains(publicHolidayModel.getLocation())){
                        PublicHolidayEntity newPublicHoliday = new PublicHolidayEntity();
                        newPublicHoliday.setPublicHolidayDate(holidayDate);
                        newPublicHoliday.setLocation(publicHolidayModel.getLocation());
                        newPublicHoliday.setPublicHolidayCreatedBy("SYS-ADMIN");
                        newPublicHoliday.setPublicHolidayCreatedOn(new Timestamp(System.currentTimeMillis()));
                        newPublicHoliday.setPublicHolidayUpdatedBy("SYS-ADMIN");
                        newPublicHoliday.setPublicHolidayUpdatedOn(new Timestamp(System.currentTimeMillis()));

                        insertUpdateCount.getAndIncrement();
                        vacationDBService.insertOrUpdatePublicHoliday(newPublicHoliday);
                    }
                }
            });
            if(insertUpdateCount.get() !=0 ){
                MessageModelResponse messageModelResponse = new MessageModelResponse();
                messageModelResponse.setMessage(insertUpdateCount.get() + " Public holidays inserted or updated");

                return new ResponseEntity<>(messageModelResponse, HttpStatus.OK);
            }else{
                MessageModelResponse messageModelResponse = new MessageModelResponse();
                messageModelResponse.setMessage("No public holiday to be inserted or updated");

                return new ResponseEntity<>(messageModelResponse, HttpStatus.OK);
            }
        }else{
            MessageModelResponse messageModelResponse = new MessageModelResponse();
            messageModelResponse.setMessage("Please pass atleast one public holiday");

            return new ResponseEntity<>(messageModelResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
