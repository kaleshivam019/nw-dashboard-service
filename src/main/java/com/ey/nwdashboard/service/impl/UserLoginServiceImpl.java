package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.UserRegisteredEntity;
import com.ey.nwdashboard.model.OAuthLoginRequest;
import com.ey.nwdashboard.model.OauthLoginResponse;
import com.ey.nwdashboard.service.UserLoginService;
import com.ey.nwdashboard.service.UserRegisteredDBService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserRegisteredDBService userRegisteredDBService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${grantType}")
    private String grantType;

    @Value("${basicAuthKey}")
    private String basicAuthKey;

    @Override
    public ResponseEntity<OauthLoginResponse> userLoginAndGetOauthToken(OAuthLoginRequest oAuthLoginRequest) {
        if(null != oAuthLoginRequest){
            String userName = oAuthLoginRequest.getUserName();
            String password = oAuthLoginRequest.getPassword();

            if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
                OauthLoginResponse oauthLoginResponse = new OauthLoginResponse();

                //Get the user from the DB to set the ROLE
                UserRegisteredEntity userRegistered = userRegisteredDBService.getUserRegistered(userName);

                //Prepare the request to call rest service using rest template
                restTemplate = new RestTemplateBuilder().messageConverters(new MappingJackson2HttpMessageConverter(new ObjectMapper()), new FormHttpMessageConverter()).build();

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", basicAuthKey);
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
                requestBody.put("username", Collections.singletonList(userName));
                requestBody.put("password", Collections.singletonList(password));
                requestBody.put("grant_type", Collections.singletonList(grantType));

                HttpEntity requestEntity = new HttpEntity<>(requestBody, headers);

                ResponseEntity<Object> response = null ;
                try{
                    //Call the rest end point to get the auth token
                    response = restTemplate.exchange("http://localhost:8080/oauth/token", HttpMethod.POST, requestEntity, Object.class);
                    /*response = restTemplate.exchange("https://nw-dashboard-service-app.herokuapp.com/oauth/token", HttpMethod.POST, requestEntity, Object.class);*/
                }catch (Exception exception){
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                if(response.getStatusCode().is2xxSuccessful()){
                    //Build the custom response to pass user role
                    LinkedHashMap<Object, Object> responseMap = (LinkedHashMap<Object, Object>) response.getBody();
                    oauthLoginResponse.setAccessToken(responseMap.get("access_token").toString());
                    oauthLoginResponse.setAccessTokenType(responseMap.get("token_type").toString());
                    oauthLoginResponse.setAccessTokenExpireIn(Long.parseLong(responseMap.get("expires_in").toString()));
                    oauthLoginResponse.setRefreshToken(responseMap.get("refresh_token").toString());
                    oauthLoginResponse.setAccessTokenScope(responseMap.get("scope").toString());
                    oauthLoginResponse.setUserRole(userRegistered.getUserRole());
                }
                return new ResponseEntity<>(oauthLoginResponse, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return null;
    }
}
