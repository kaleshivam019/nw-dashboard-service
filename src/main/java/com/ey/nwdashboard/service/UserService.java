package com.ey.nwdashboard.service;

import com.ey.nwdashboard.model.UserModel;
import com.ey.nwdashboard.model.UserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public UserResponse getUsersOnLoad();
    public ResponseEntity addNewUser(UserModel userModel);
}
