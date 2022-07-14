package com.ey.nwdashboard.service;

import com.ey.nwdashboard.model.OnLoadResponse;
import com.ey.nwdashboard.model.UserModel;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public OnLoadResponse getOnLoadData();
    public ResponseEntity addNewUser(UserModel userModel);
}
