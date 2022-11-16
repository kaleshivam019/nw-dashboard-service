package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.UserRegisteredEntity;
import com.ey.nwdashboard.model.MessageModelResponse;
import com.ey.nwdashboard.service.UserRegisteredDBService;
import com.ey.nwdashboard.service.UserSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

@Service
public class UserSignUpServiceImpl implements UserSignUpService {

    @Autowired
    UserRegisteredDBService userRegisteredDBService;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public ResponseEntity<MessageModelResponse> userSignUp(UserRegisteredEntity userRegisteredEntity) {
        MessageModelResponse messageModelResponse = new MessageModelResponse();
        //Get the user from DB to check if it is existing user
        if(null != userRegisteredEntity && null != userRegisteredEntity.getUserName()){
            UserRegisteredEntity existingUserRegisteredEntity = userRegisteredDBService.getUserRegistered(userRegisteredEntity.getUserName());
            if(null != existingUserRegisteredEntity){
                messageModelResponse.setMessage("Username is already registered!");
                return new ResponseEntity<>(messageModelResponse, HttpStatus.BAD_REQUEST);
            }else{
                userRegisteredDBService.saveUser(userRegisteredEntity.getUserName(), userRegisteredEntity.getUserPassword(), userRegisteredEntity.getUserRole());
                messageModelResponse.setMessage("User successfully registered!");
                return new ResponseEntity<>(messageModelResponse, HttpStatus.CREATED);
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<MessageModelResponse> userLogOut(String oauthToken) {
        MessageModelResponse messageModelResponse = new MessageModelResponse();
        try {
            if (null != oauthToken && !oauthToken.isEmpty()) {
                String tokenValue = oauthToken;

                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);


                OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
                tokenStore.removeRefreshToken(refreshToken);
            }
        } catch (Exception e) {
            messageModelResponse.setMessage("Invalid access token");
            return new ResponseEntity<>(messageModelResponse, HttpStatus.BAD_REQUEST);
        }
        messageModelResponse.setMessage("User logged out successfully");
        return new ResponseEntity<>(messageModelResponse, HttpStatus.OK);
    }
}
