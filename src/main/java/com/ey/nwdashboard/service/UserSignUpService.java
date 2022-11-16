package com.ey.nwdashboard.service;

import com.ey.nwdashboard.entity.UserRegisteredEntity;
import com.ey.nwdashboard.model.MessageModelResponse;
import org.springframework.http.ResponseEntity;

public interface UserSignUpService {
    ResponseEntity<MessageModelResponse> userSignUp(UserRegisteredEntity userRegisteredEntity);
    ResponseEntity<MessageModelResponse> userLogOut(String oauthToken);
}
