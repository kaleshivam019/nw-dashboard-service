package com.ey.nwdashboard.utils;

import com.ey.nwdashboard.entity.UserEntity;
import com.ey.nwdashboard.model.UserModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DashboardUtils {
    public static List<UserModel> convertEntityToModel(List<UserEntity> userEntityList) {
        List<UserModel> userModelList = new ArrayList<>();

        if (null != userEntityList && !userEntityList.isEmpty()){
            userEntityList.stream().forEach(userEntity -> {
                UserModel userModel = new UserModel();

                userModel.setUserGPN(userEntity.getUserGPN());
                userModel.setUserName(userEntity.getUserName());
                userModel.setUserEmail(userEntity.getUserEmail());
                userModel.setUserProjectName(userEntity.getUserProjectName());
                userModel.setUserActive(userEntity.isUserActive());
                userModel.setUserCreatedBy(userEntity.getUserCreatedBy());
                userModel.setUserCreatedOn(userEntity.getUserCreatedOn());
                userModel.setUserUpdatedBy(userEntity.getUserUpdatedBy());
                userModel.setUserUpdatedOn(userEntity.getUserUpdatedOn());

                userModelList.add(userModel);
            });
            return userModelList;
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
            userEntity.setUserCreatedBy("SYS-ADMIN");
            userEntity.setUserCreatedOn(new Timestamp(System.currentTimeMillis()));
        }
        return userEntity;
    }
}
