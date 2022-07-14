package com.ey.nwdashboard.controller;

import com.ey.nwdashboard.model.OnLoadResponse;
import com.ey.nwdashboard.model.UserModel;
import com.ey.nwdashboard.model.VacationRequest;
import com.ey.nwdashboard.model.VacationResponse;
import com.ey.nwdashboard.service.UserService;
import com.ey.nwdashboard.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NWDashboardController {

    @Autowired
    UserService userService;

    @Autowired
    VacationService vacationService;

    @CrossOrigin
    @GetMapping(value = "dashboard/v1/load", produces = "application/json")
    public OnLoadResponse getOnLoadData(){
        return userService.getOnLoadData();
    }

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/add/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity addNewUser(@RequestBody UserModel userModel){
        return userService.addNewUser(userModel);
    }

    @CrossOrigin
    @GetMapping(value = "dashboard/v1/{user-gpn}/vacations", produces = "application/json")
    public VacationResponse getUserVacations(@PathVariable("user-gpn") String userGPN){
        return vacationService.getUserVacations(userGPN);
    }

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/add/vacations", consumes = "application/json", produces = "application/json")
    public ResponseEntity saveUserVacations(@RequestBody VacationRequest vacationRequest){
        return vacationService.saveUserVacations(vacationRequest);
    }
}
