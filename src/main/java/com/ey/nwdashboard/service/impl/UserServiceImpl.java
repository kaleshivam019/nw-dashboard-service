package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.UserEntity;
import com.ey.nwdashboard.model.UserModel;
import com.ey.nwdashboard.model.UserResponse;
import com.ey.nwdashboard.service.UserDBService;
import com.ey.nwdashboard.service.UserService;
import com.ey.nwdashboard.utils.DashboardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDBService userDBService;

    /**
     * This method is responsible to get all the active users from DB
     * @return
     */
    @Override
    public UserResponse getUsersOnLoad() {
        UserResponse userResponse = new UserResponse();

        //Get all the users from DB
        List<UserEntity> userEntityList = userDBService.getAllUsers();
        if(null != userEntityList && !userEntityList.isEmpty()){
            //Convert the UserEntity to UserModel using utility method
            List<UserModel> userModelList = DashboardUtils.convertEntityToModel(userEntityList);

            userResponse.setUserList(userModelList);

            return userResponse;
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
                if(null != userEntity) {
                    return ResponseEntity.ok("User Created");
                }
            }
        }
        return ResponseEntity.ok("User already exists");
    }
}
