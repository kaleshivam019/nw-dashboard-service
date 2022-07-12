package com.ey.nwdashboard.controller;

import com.ey.nwdashboard.model.UserModel;
import com.ey.nwdashboard.model.UserResponse;
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

    @CrossOrigin(origins = {"http://localhost:3000"})
    @GetMapping(value = "dashboard/v1/users", produces = "application/json")
    public UserResponse getUsersOnLoad(){
        return userService.getUsersOnLoad();
    }

    @CrossOrigin(origins = {"http://localhost:3000"})
    @PostMapping(value = "dashboard/v1/add/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity addNewUser(@RequestBody UserModel userModel){
        return userService.addNewUser(userModel);
    }

    @CrossOrigin(origins = {"http://localhost:3000"})
    @GetMapping(value = "dashboard/v1/{user-gpn}/vacations", produces = "application/json")
    public VacationResponse getUserVacations(@PathVariable("user-gpn") String userGPN){
        return vacationService.getUserVacations(userGPN);
    }

    @CrossOrigin(origins = {"http://localhost:3000"})
    @PostMapping(value = "dashboard/v1/add/vacations", consumes = "application/json", produces = "application/json")
    public ResponseEntity saveUserVacations(@RequestBody VacationRequest vacationRequest){
        return vacationService.saveUserVacations(vacationRequest);
    }
}
