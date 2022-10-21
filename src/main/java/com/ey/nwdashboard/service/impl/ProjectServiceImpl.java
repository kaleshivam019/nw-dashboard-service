package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.constants.DashboardConstants;
import com.ey.nwdashboard.entity.ProjectEntity;
import com.ey.nwdashboard.model.MessageModelResponse;
import com.ey.nwdashboard.model.ProjectModel;
import com.ey.nwdashboard.service.ProjectDBService;
import com.ey.nwdashboard.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectDBService projectDBService;

    @Override
    public ResponseEntity<List<ProjectModel>> fetchProjects() {
        List<ProjectModel> projectModelsList = new ArrayList<>();
        List<ProjectEntity> allProjects;
        try{
            allProjects = projectDBService.getAllProjects();
        }catch (Exception exception){
            return new ResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(allProjects.size()>0){
            allProjects.stream().forEach(projectEntity -> {
                ProjectModel projectModel = new ProjectModel();
                projectModel.setProjectName(projectEntity.getProjectName());
                projectModel.setLine(projectEntity.getProjectLine());
                projectModel.setLeadName(projectEntity.getProjectLeadName());
                projectModelsList.add(projectModel);
            });
            return new ResponseEntity<>(projectModelsList, HttpStatus.OK);
        } else{
            return null;
        }
    }

    /**
     * This service will add the project in the DB
     * @param projectModelList
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<MessageModelResponse> addProject(List<ProjectModel> projectModelList) {
        MessageModelResponse messageModelResponse = new MessageModelResponse();
        Set<String> existingProjectsSet = new HashSet<>();

        if(null != projectModelList){
            List<ProjectEntity> existingProjects = projectDBService.getAllProjects();

            //Check if any project is already present then add it to set
            for(ProjectModel projectModel : projectModelList){
                if(null != existingProjects && !existingProjects.isEmpty()){
                    existingProjects.forEach(projectEntity -> {
                        if(projectEntity.getProjectName().equalsIgnoreCase(projectModel.getProjectName()) &&
                                projectEntity.getProjectLine().equalsIgnoreCase(projectModel.getLine())){
                            existingProjectsSet.add(projectEntity.getProjectName());
                        }
                    });
                }
            }

            //If all the requested projects are already present
            if(null != existingProjects &&
                    null != existingProjectsSet &&
                    (existingProjects.size() == existingProjectsSet.size())){
                messageModelResponse.setMessage("All projects are already present in DB!");
                return new ResponseEntity<>(messageModelResponse, HttpStatus.OK);
            }

            for (ProjectModel projectModel : projectModelList){
                if(!existingProjectsSet.contains(projectModel.getProjectName())){
                    ProjectEntity projectEntity = new ProjectEntity();

                    projectEntity.setProjectName(projectModel.getProjectName());
                    projectEntity.setProjectLine(projectModel.getLine());
                    projectEntity.setProjectLeadName(projectModel.getLeadName());
                    projectEntity.setProjectCreatedBy(DashboardConstants.SYS_ADMIN);
                    projectEntity.setProjectCreatedOn(new Timestamp(System.currentTimeMillis()));

                    try{
                        projectDBService.addProject(projectEntity);
                    } catch (Exception exception){
                        messageModelResponse.setMessage("Error while saving project in DB!");
                        return new ResponseEntity<>(messageModelResponse, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
            messageModelResponse.setMessage("Project(s) successfully saved in DB!");
            return new ResponseEntity<>(messageModelResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(messageModelResponse, HttpStatus.OK);
    }
}
