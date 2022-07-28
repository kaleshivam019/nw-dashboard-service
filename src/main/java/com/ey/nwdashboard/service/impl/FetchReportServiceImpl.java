package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.ProjectEntity;
import com.ey.nwdashboard.entity.UserEntity;
import com.ey.nwdashboard.entity.VacationEntity;
import com.ey.nwdashboard.model.FetchReportLeaveModel;
import com.ey.nwdashboard.model.FetchReportProjectModel;
import com.ey.nwdashboard.model.FetchReportResponse;
import com.ey.nwdashboard.model.FetchReportUserModel;
import com.ey.nwdashboard.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class FetchReportServiceImpl implements FetchReportService {

    @Autowired
    ProjectDBService projectDBService;

    @Autowired
    UserDBService userDBService;

    @Autowired
    VacationDBService vacationDBService;

    @Override
    public ResponseEntity fetchReport() {
        try{
            //Setting start date & end date of current month & next month
            LocalDate currentMonthStartDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            LocalDate currentMonthEndDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
            LocalDate nextMonthStartDate = LocalDate.now().plusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
            LocalDate nextMonthEndDate = LocalDate.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

            //Prepare Response
            FetchReportResponse fetchReportResponse = new FetchReportResponse();

            //Get all the projects
            List<ProjectEntity> projects = projectDBService.getAllProjects();

            //Get all the users
            List<UserEntity> users = userDBService.getAllUsers();

            //For each project get all the users & their leaves
            if(null != projects){

                List<FetchReportProjectModel> projectModels = new ArrayList<>();

                projects.forEach(projectEntity -> {
                    String projectName = projectEntity.getProjectName();

                    FetchReportProjectModel fetchReportProjectModel = new FetchReportProjectModel();
                    fetchReportProjectModel.setProjectName(projectName);

                    //Get project specific users
                    if(null != users){

                        List<FetchReportUserModel> userModels = new ArrayList<>();

                        users.forEach(user -> {
                            if(user.getUserProjectName().equalsIgnoreCase(projectName)){
                                String userGPN = user.getUserGPN();
                                String userName = user.getUserName();

                                FetchReportUserModel fetchReportUserModel = new FetchReportUserModel();
                                fetchReportUserModel.setUserName(userName);

                                FetchReportLeaveModel fetchReportLeaveModel = new FetchReportLeaveModel();
                                List<String> currentMonthLeaves = new ArrayList<>();
                                List<String> nextMonthLeaves = new ArrayList<>();

                                //Get vacations for specific user
                                List<VacationEntity> userVacations = vacationDBService.getVacations(userGPN);

                                //check if vacations is in between startDate & endDate
                                if (null != userVacations){
                                    userVacations.forEach(userVacation -> {
                                        //Get the vacation date & parse it LocalDate for comparison
                                        LocalDate vacationDate = LocalDate.parse(userVacation.getVacationDate().toString());

                                        //Check for current month vacations & next month vacation
                                        if((vacationDate.isEqual(currentMonthStartDate)) ||
                                                (vacationDate.isEqual(currentMonthEndDate)) ||
                                                (vacationDate.isAfter(currentMonthStartDate) && vacationDate.isBefore(currentMonthEndDate))){
                                            currentMonthLeaves.add(vacationDate.toString());
                                        }else if((vacationDate.isEqual(nextMonthStartDate)) ||
                                                (vacationDate.isEqual(nextMonthEndDate)) ||
                                                (vacationDate.isAfter(nextMonthStartDate) && vacationDate.isBefore(nextMonthEndDate))){
                                            nextMonthLeaves.add(vacationDate.toString());
                                        }
                                    });
                                    fetchReportLeaveModel.setCurrentMonth(currentMonthLeaves);
                                    fetchReportLeaveModel.setNextMonth(nextMonthLeaves);
                                    fetchReportUserModel.setLeaves(fetchReportLeaveModel);
                                }
                                userModels.add(fetchReportUserModel);
                            }
                        });
                        fetchReportProjectModel.setUsers(userModels);
                    }
                    projectModels.add(fetchReportProjectModel);
                });
                fetchReportResponse.setProjects(projectModels);
            }
            if(null != fetchReportResponse){
                return new ResponseEntity(fetchReportResponse, HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
