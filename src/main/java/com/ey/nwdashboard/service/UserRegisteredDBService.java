package com.ey.nwdashboard.service;

import com.ey.nwdashboard.entity.UserRegisteredEntity;

public interface UserRegisteredDBService {
    UserRegisteredEntity getUserRegistered(String userName);
    UserRegisteredEntity saveUser(String userName, String userPassword, String userRole);
}
