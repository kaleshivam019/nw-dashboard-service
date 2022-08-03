package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.ProjectEntity;
import com.ey.nwdashboard.model.ProjectModel;
import com.ey.nwdashboard.service.FetchProjectService;
import com.ey.nwdashboard.service.ProjectDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchProjectServiceImpl implements FetchProjectService {

    @Autowired
    ProjectDBService projectDBService;

    @Override
    public List<ProjectModel> fetchProjects() {
        List<ProjectModel> projectModelsList = new ArrayList<>();
        List<ProjectEntity> allProjects = projectDBService.getAllProjects();
        allProjects.stream().forEach(projectEntity -> {
            ProjectModel projectModel = new ProjectModel();
            projectModel.setProjectName(projectEntity.getProjectName());
            projectModel.setLine(projectEntity.getProjectLine());
            projectModel.setLeadName(projectEntity.getProjectLeadName());
            projectModelsList.add(projectModel);
        });
        return projectModelsList;
    }
}
