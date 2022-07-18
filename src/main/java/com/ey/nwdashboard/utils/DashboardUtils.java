package com.ey.nwdashboard.utils;

import com.ey.nwdashboard.entity.TrackerEntity;
import com.ey.nwdashboard.entity.UserEntity;
import com.ey.nwdashboard.entity.VacationEntity;
import com.ey.nwdashboard.model.UserModel;
import com.ey.nwdashboard.model.VacationModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DashboardUtils {
    public static UserModel convertEntityToModel(UserEntity userEntity, TrackerEntity trackerEntity) {
        UserModel userModel = new UserModel();
        if(null != userEntity){
            userModel.setUserGPN(userEntity.getUserGPN());
            userModel.setUserName(userEntity.getUserName());
            userModel.setUserEmail(userEntity.getUserEmail());
            userModel.setUserProjectName(userEntity.getUserProjectName());
            userModel.setUserActive(userEntity.isUserActive());
            userModel.setUserLocation(userEntity.getUserLocation());
            userModel.setUserCreatedBy(userEntity.getUserCreatedBy());
            userModel.setUserCreatedOn(userEntity.getUserCreatedOn());
            userModel.setUserUpdatedBy(userEntity.getUserUpdatedBy());
            userModel.setUserUpdatedOn(userEntity.getUserUpdatedOn());
            if(null != trackerEntity){
                userModel.setVacation(trackerEntity.isVacation());
                userModel.setAllowance(trackerEntity.isAllowance());
                userModel.setShift(trackerEntity.isShift());
            }
            return userModel;
        }
        return null;
    }

    /**
     * Utility method to convert model to entity
     * @param userModel
     * @return
     */
    public static UserEntity convertModelToEntity(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        if(null != userModel){
            userEntity.setUserGPN(userModel.getUserGPN());
            userEntity.setUserName(userModel.getUserName());
            userEntity.setUserEmail(userModel.getUserEmail());
            userEntity.setUserProjectName(userModel.getUserProjectName());
            userEntity.setUserActive(userModel.isUserActive());
            userEntity.setUserLocation(userModel.getUserLocation());
            userEntity.setUserCreatedBy("SYS-ADMIN");
            userEntity.setUserCreatedOn(new Timestamp(System.currentTimeMillis()));
            userEntity.setUserUpdatedBy("SYS-ADMIN");
            userEntity.setUserUpdatedOn(new Timestamp(System.currentTimeMillis()));
        }
        return userEntity;
    }

    /**
     * Utility method to convert vacation entity to model
     * @param vacationEntityList
     * @return
     */
    public static List<VacationModel> convertVacationEntityToModel(List<VacationEntity> vacationEntityList) {
        List<VacationModel> vacationModelList = new ArrayList<>();
        vacationEntityList.stream().forEachOrdered(vacationEntity -> {
            VacationModel vacationModel = new VacationModel();

            vacationModel.setVacationDate(vacationEntity.getVacationDate().toString());
            vacationModel.setVacationPlanned(vacationEntity.isVacationPlanned());
            vacationModel.setVacationFullDay(vacationEntity.isVacationFullDay());
            vacationModel.setPublicHoliday(vacationEntity.isPublicHoliday());

            vacationModelList.add(vacationModel);
        });
        if(null != vacationModelList && !vacationModelList.isEmpty()){
            return vacationModelList;
        }else{
            return null;
        }
    }
}
