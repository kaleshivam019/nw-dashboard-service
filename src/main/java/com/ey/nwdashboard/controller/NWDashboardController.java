package com.ey.nwdashboard.controller;

import com.ey.nwdashboard.entity.TrackerEntity;
import com.ey.nwdashboard.entity.UserEntity;
import com.ey.nwdashboard.entity.UserRegisteredEntity;
import com.ey.nwdashboard.entity.VacationEntity;
import com.ey.nwdashboard.model.*;
import com.ey.nwdashboard.repository.TrackerRepository;
import com.ey.nwdashboard.repository.UserRepository;
import com.ey.nwdashboard.repository.VacationRepository;
import com.ey.nwdashboard.service.*;
import org.hibernate.internal.util.Cloneable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class NWDashboardController {

    @Autowired
    UserService userService;

    @Autowired
    VacationService vacationService;

    @Autowired
    FetchReportService fetchReportService;

    @Autowired
    DownloadUserManualService downloadUserManualService;
    @Autowired
    ProjectService projectService;

    @Autowired
    SchedulerService schedulerService;

    @Autowired
    UserSignUpService userSignUpService;

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    DBDataService dbDataService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VacationRepository vacationRepository;

    @Autowired
    TrackerRepository trackerRepository;

    @CrossOrigin
    @GetMapping(value = "dashboard/v1/load", produces = "application/json")
    public OnLoadResponse getOnLoadData(){
        return userService.getOnLoadData();
    }

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/add/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity addNewUser(@RequestBody List<UserModel> userModelList){
        return userService.addNewUser(userModelList);
    }

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/remove/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageModelResponse> removeUser(@RequestBody List<UserModel> userModelList){
        return userService.removeUser(userModelList);
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
        return projectService.fetchProjects();
    }

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/add/project", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageModelResponse> addProject(@RequestBody List<ProjectModel> projectModelList){
        return projectService.addProject(projectModelList);
    }

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/generate-report")
    public ResponseEntity generateExcelReport(@RequestParam(name = "startDate", required = false) String startDate,
                                              @RequestParam(name = "endDate", required = false) String endDate,
                                              @RequestParam(name = "userGPN", required = false) String userGPN){
        //Date validation - start
        if((null != startDate && !startDate.isBlank()) && (null != endDate && !endDate.isBlank())){
            LocalDate localStartDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate localEndDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (localStartDate.isAfter(localEndDate)){
                return new ResponseEntity(new ReportResponse("Failed", "Date validation error[Start date is after End date]"), HttpStatus.BAD_REQUEST);
            }
        }

        if((null != startDate && !startDate.isBlank()) && (null != endDate && !endDate.isBlank()) ||
                ((null == startDate || startDate.isBlank()) && (null == endDate || endDate.isBlank()))){
            return fetchReportService.generateReport(startDate, endDate, userGPN);
        }
        //Date validation - end
        return new ResponseEntity(new ReportResponse("Failed", "Date validation error[Either both date should be present or both should not present]"), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @GetMapping(value = "dashboard/v1/download-report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity downloadExcelReport() {
        return fetchReportService.downloadFile();
    }

    @CrossOrigin
    @GetMapping(value = "dashboard/v1/download-user-manual", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity downloadUserManual() {
        return downloadUserManualService.downloadFile();
    }

    @CrossOrigin
    @GetMapping(value = "dashboard/v1/refresh")
    public MessageModelResponse refreshApplication(){
        MessageModelResponse messageModelResponse = new MessageModelResponse();
        messageModelResponse.setMessage("Success");
        Logger.getLogger("NWDashboardController").log(Level.INFO, "NW Dashboard Application Refreshed using Schedular");
        return messageModelResponse;
    }

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/defaulting-entry")
    public ResponseEntity<MessageModelResponse> defaultingPendingVacationTrackerEntry(){
        return schedulerService.defaultingPendingVacationTrackerEntry();
    }

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/user-sign-up")
    public ResponseEntity<MessageModelResponse> userSignUp(@RequestBody UserRegisteredEntity userRegisteredEntity){
        return userSignUpService.userSignUp(userRegisteredEntity);
    }

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/user-log-out")
    public ResponseEntity<MessageModelResponse> userLogOut(@RequestHeader(value = "Authorization") String oauthToken){
        return userSignUpService.userLogOut(oauthToken);
    }

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/user-log-in", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OauthLoginResponse> userLogIn(@RequestBody OAuthLoginRequest oAuthLoginRequest){
        return userLoginService.userLoginAndGetOauthToken(oAuthLoginRequest);
    }

    @CrossOrigin
    @GetMapping(value = "dashboard/v1/retrieve-db-data", produces = "application/json")
    public ResponseEntity<DBDataRequestResponse> retrieveDBData(){
        return dbDataService.retrieveDBData();
    }

    @CrossOrigin
    @PostMapping(value = "dashboard/v1/insert-db-data", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageModelResponse> insertDBData(@RequestBody DBDataRequestResponse dbDataRequestResponse){
        return dbDataService.insertDBData(dbDataRequestResponse);
    }

   /* @CrossOrigin
    @PostMapping(value = "dashboard/v1/migrate-gpn", produces = "application/json")
    public ResponseEntity migrateGPNToUniqueIdentifier(){
        //Fetch all the users

        List<UserEntity> userEntityList = userRepository.findAll();

        if(null != userEntityList){
            for(UserEntity userEntity : userEntityList){
                UserEntity userEntityNew = userEntity;
                String uniqueId = UUID.randomUUID().toString();

                List<VacationEntity> vacationEntityList = vacationRepository.findAllByvacationUserGPN(userEntity.getUserGPN());
                TrackerEntity trackerEntity = trackerRepository.findBytrackerUserGPN(userEntity.getUserGPN());

                //Update all the tracker entry to unique Id
                if(null != trackerEntity){
                    trackerEntity.setTrackerUserGPN(uniqueId);
                    trackerRepository.save(trackerEntity);
                }

                //Update all the vacation entry to unique Id
                if(null != vacationEntityList){
                    for(VacationEntity vacationEntity : vacationEntityList){
                        if(null != vacationEntity){
                            vacationEntity.setVacationUserGPN(uniqueId);
                            vacationRepository.save(vacationEntity);
                        }
                    }
                }

                //Delete the existing row from the table
                userRepository.delete(userEntity);

                //Update all the user entry to unique Id and save it
                userEntityNew.setUserGPN(uniqueId);
                userRepository.save(userEntity);
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }*/
}
