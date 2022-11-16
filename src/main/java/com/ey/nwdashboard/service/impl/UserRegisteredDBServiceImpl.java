package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.UserRegisteredEntity;
import com.ey.nwdashboard.repository.UserRegisteredRepository;
import com.ey.nwdashboard.service.UserRegisteredDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegisteredDBServiceImpl implements UserRegisteredDBService {

    @Autowired
    private UserRegisteredRepository userRegisteredRepository;

    @Override
    public UserRegisteredEntity getUserRegistered(String userName) {
        UserRegisteredEntity userRegisteredEntity = userRegisteredRepository.findByuserName(userName);
        if(null != userRegisteredEntity){
            return userRegisteredEntity;
        }
        return null;
    }

    @Override
    public UserRegisteredEntity saveUser(String userName, String userPassword, String userRole) {
        if((null != userName && !userName.isBlank()) &&
                (null != userPassword && !userPassword.isBlank()) &&
                    (null != userRole && !userRole.isBlank())){

            UserRegisteredEntity userRegisteredEntity = new UserRegisteredEntity();
            userRegisteredEntity.setUserName(userName);
            userRegisteredEntity.setUserPassword(userPassword);
            userRegisteredEntity.setUserRole(userRole);

            return userRegisteredRepository.save(userRegisteredEntity);
        }
        return null;
    }
}
