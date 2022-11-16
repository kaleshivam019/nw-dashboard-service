package com.ey.nwdashboard.service;

import com.ey.nwdashboard.model.OAuthLoginRequest;
import com.ey.nwdashboard.model.OauthLoginResponse;
import org.springframework.http.ResponseEntity;

public interface UserLoginService {
    ResponseEntity<OauthLoginResponse> userLoginAndGetOauthToken(OAuthLoginRequest oAuthLoginRequest);
}
