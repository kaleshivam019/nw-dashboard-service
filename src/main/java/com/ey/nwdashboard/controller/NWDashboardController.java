package com.ey.nwdashboard.controller;

import com.ey.nwdashboard.model.UserModel;
import com.ey.nwdashboard.model.UserResponse;
import com.ey.nwdashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NWDashboardController {

    @Autowired
    UserService userService;

    @GetMapping(value = "dashboard/v1/users", produces = "application/json")
    public UserResponse getUsersOnLoad(){
        return userService.getUsersOnLoad();
    }

    @PostMapping(value = "dashboard/v1/add/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity addNewUser(@RequestBody UserModel userModel){
        return userService.addNewUser(userModel);
    }
}
