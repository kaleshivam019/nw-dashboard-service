package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.constants.DashboardConstants;
import com.ey.nwdashboard.entity.TrackerEntity;
import com.ey.nwdashboard.entity.UserEntity;
import com.ey.nwdashboard.entity.VacationEntity;
import com.ey.nwdashboard.model.CurrentDayVacationModel;
import com.ey.nwdashboard.model.OnLoadResponse;
import com.ey.nwdashboard.model.UserModel;
import com.ey.nwdashboard.model.UserModelResponse;
import com.ey.nwdashboard.service.TrackerDBService;
import com.ey.nwdashboard.service.UserDBService;
import com.ey.nwdashboard.service.UserService;
import com.ey.nwdashboard.service.VacationDBService;
import com.ey.nwdashboard.utils.DashboardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDBService userDBService;

    @Autowired
    TrackerDBService trackerDBService;

    @Autowired
    VacationDBService vacationDBService;

    /**
     * This method is responsible to get data on load
     * @return
     */
    @Override
    public OnLoadResponse getOnLoadData() {
        OnLoadResponse onLoadResponse = new OnLoadResponse();

        //Get all the users from DB
        List<UserEntity> userEntityList = userDBService.getAllUsers();

        if(null != userEntityList && !userEntityList.isEmpty()){
            List<UserModel> userModelList = new ArrayList<>();
            List<CurrentDayVacationModel> currentDayVacationModels = new ArrayList<>();
            AtomicInteger currentDayLeaveCount = new AtomicInteger();

            userEntityList.stream().forEach(userEntity -> {

                String userGPN = userEntity.getUserGPN();

                //Get tracker data for each entity
                TrackerEntity trackerEntity = trackerDBService.getTrackerEntry(userGPN);

                //Convert the UserEntity & trackerEntity to UserModel using utility method
                userModelList.add(DashboardUtils.convertEntityToModel(userEntity, trackerEntity));

                //Get Vacation for each user with GPN and filter based on today's date - START
                List<VacationEntity> vacationEntityList = vacationDBService.getVacations(userGPN);
                LocalDate currentDate = LocalDate.now();

                VacationEntity currentDayVacationEntity = null == vacationEntityList ? null : vacationEntityList.stream().filter(vacationEntity ->
                        vacationEntity.getVacationDate().toString().equals(currentDate.toString())
                ).findFirst().orElse(null);

                if(null != currentDayVacationEntity){
                    currentDayLeaveCount.getAndIncrement();

                    CurrentDayVacationModel currentDayVacationModel = new CurrentDayVacationModel();

                    currentDayVacationModel.setId(currentDayLeaveCount.get());
                    currentDayVacationModel.setName(userEntity.getUserName());
                    currentDayVacationModel.setEmail(userEntity.getUserEmail());
                    currentDayVacationModel.setTeam(userEntity.getUserProjectName());
                    currentDayVacationModel.setLocation(userEntity.getUserLocation());
                    currentDayVacationModel.setPublicHoliday(currentDayVacationEntity.isPublicHoliday());
                    currentDayVacationModel.setVacationPlanned(currentDayVacationEntity.isVacationPlanned());
                    currentDayVacationModel.setVacationFullDay(currentDayVacationEntity.isVacationFullDay());

                    currentDayVacationModels.add(currentDayVacationModel);
                }
                //END
            });

            onLoadResponse.setUsers(userModelList);
            onLoadResponse.setOnVacationToday(currentDayVacationModels);

            return onLoadResponse;
        }
        return null;
    }

    /**
     * This method is responsible to add a new user or update existing user
     * @return responseEntity
     */
    @Override
    public ResponseEntity addNewUser(UserModel userModel) {
        try{
            if(null != userModel.getUserGPN() &&
                    !userDBService.isExistingUser(userModel.getUserGPN())){
                UserEntity newUserEntity = DashboardUtils.convertModelToEntity(userModel, DashboardConstants.ADD_USER);
                if (null != newUserEntity){
                    UserEntity createdUserEntity = userDBService.addNewUser(newUserEntity);
                    if(null != createdUserEntity){
                        return new ResponseEntity(prepareUserModelResponse(), HttpStatus.CREATED);
                    }
                }
            }else if(null != userModel.getUserGPN() &&
                    userDBService.isExistingUser(userModel.getUserGPN())){
                UserEntity updateUserEntity = DashboardUtils.convertModelToEntity(userModel, DashboardConstants.UPDATE_USER);

                UserEntity existingUserEntity = userDBService.getAllUsers().stream().filter(userEntity -> userEntity.getUserGPN().equals(userModel.getUserGPN())).findFirst().orElse(null);
                if(null != existingUserEntity){
                    //set createdBy & createdOn from DB
                    updateUserEntity.setUserCreatedBy(existingUserEntity.getUserCreatedBy());
                    updateUserEntity.setUserCreatedOn(existingUserEntity.getUserCreatedOn());

                    if (null != updateUserEntity){
                        UserEntity updatedUserEntity = userDBService.addNewUser(updateUserEntity);
                        if(null != updatedUserEntity){
                            return new ResponseEntity(prepareUserModelResponse(), HttpStatus.OK);
                        }
                    }
                }
            }
        }catch (Exception exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    /**
     * This method will get all users from DB prepare the response for addUser API
     * @return
     */
    private UserModelResponse prepareUserModelResponse() {
        //Initialize the UserModel Object
        UserModelResponse userModelResponse = new UserModelResponse();

        //Get all the users from DB
        List<UserEntity> userEntityList = userDBService.getAllUsers();
        if(null != userEntityList && !userEntityList.isEmpty()) {
            List<UserModel> userModelList = new ArrayList<>();
            userEntityList.stream().forEach(userEntity -> {

                String userGPN = userEntity.getUserGPN();

                //Get tracker data for each entity
                TrackerEntity trackerEntity = trackerDBService.getTrackerEntry(userGPN);

                //Convert the UserEntity & trackerEntity to UserModel using utility method
                userModelList.add(DashboardUtils.convertEntityToModel(userEntity, trackerEntity));
            });
            userModelResponse.setUserModelList(userModelList);
        }
        return userModelResponse;
    }

}
