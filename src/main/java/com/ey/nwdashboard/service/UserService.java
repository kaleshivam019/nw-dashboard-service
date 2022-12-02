package com.ey.nwdashboard.service;

import com.ey.nwdashboard.model.MessageModelResponse;
import com.ey.nwdashboard.model.OnLoadResponse;
import com.ey.nwdashboard.model.UserModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public OnLoadResponse getOnLoadData();
    public ResponseEntity addNewUser(List<UserModel> userModelList);
    ResponseEntity<MessageModelResponse> removeUser(List<UserModel> userModelList);
}
