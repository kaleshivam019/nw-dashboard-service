package com.ey.nwdashboard.service;

import com.ey.nwdashboard.entity.UserEntity;

import java.util.List;

public interface UserDBService {
    public List<UserEntity> getAllUsers();
    public boolean isExistingUser(String userGPN);
    public UserEntity addNewUser(UserEntity userEntity);
    public void deleteExistingUser(UserEntity userEntity);
    public UserEntity getUserByGPN(String userGPN);
}
