package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.TrackerEntity;
import com.ey.nwdashboard.entity.UserEntity;
import com.ey.nwdashboard.entity.VacationEntity;
import com.ey.nwdashboard.model.CurrentDayVacationModel;
import com.ey.nwdashboard.model.OnLoadResponse;
import com.ey.nwdashboard.model.UserModel;
import com.ey.nwdashboard.service.TrackerDBService;
import com.ey.nwdashboard.service.UserDBService;
import com.ey.nwdashboard.service.UserService;
import com.ey.nwdashboard.service.VacationDBService;
import com.ey.nwdashboard.utils.DashboardUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
     * This method is responsible to add a new user
     * @return responseEntity
     */
    @Override
    public ResponseEntity addNewUser(UserModel userModel) {
        if(null != userModel.getUserGPN() &&
                !userDBService.isExistingUser(userModel.getUserGPN())){
            UserEntity userEntity = DashboardUtils.convertModelToEntity(userModel);
            if (null != userEntity){
                UserEntity createdUserEntity = userDBService.addNewUser(userEntity);
                if(null != createdUserEntity) {
                    return ResponseEntity.ok("User Created");
                }
            }
        }
        return ResponseEntity.ok("User already exists");
    }
}
