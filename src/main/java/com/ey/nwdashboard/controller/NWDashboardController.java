package com.ey.nwdashboard.controller;

import com.ey.nwdashboard.model.*;
import com.ey.nwdashboard.service.FetchProjectService;
import com.ey.nwdashboard.service.FetchReportService;
import com.ey.nwdashboard.service.UserService;
import com.ey.nwdashboard.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NWDashboardController {

    @Autowired
    UserService userService;

    @Autowired
    VacationService vacationService;

    @Autowired
    FetchReportService fetchReportService;

    @Autowired
    FetchProjectService fetchProjectService;

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

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/add/public-holidays", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageModelResponse> savePublicHolidays(@RequestBody PublicHolidayRequest publicHolidayRequest){
        return vacationService.savePublicHolidays(publicHolidayRequest);
    }

    @CrossOrigin
    @GetMapping(value = "dashboard/v1/fetch-report", produces = "application/json")
    public ResponseEntity<FetchReportResponse> fetchReport(){
        return fetchReportService.fetchReport();
    }

    @CrossOrigin
    @GetMapping(value = "dashboard/v1/fetch-projects", produces = "application/json")
    public ResponseEntity<List<ProjectModel>> fetchProject(){
        return fetchProjectService.fetchProjects();
    }
}
