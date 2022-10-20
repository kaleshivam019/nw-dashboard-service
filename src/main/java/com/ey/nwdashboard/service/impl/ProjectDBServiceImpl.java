package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.ProjectEntity;
import com.ey.nwdashboard.repository.ProjectRepository;
import com.ey.nwdashboard.service.ProjectDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectDBServiceImpl implements ProjectDBService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public List<ProjectEntity> getAllProjects() {

        List<ProjectEntity> projectEntityList = projectRepository.findAll();
        if(null != projectEntityList && !projectEntityList.isEmpty()){
            return projectEntityList;
        }
        return null;
    }

    @Override
    public ProjectEntity addProject(ProjectEntity projectEntity) {
        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
        if(null != savedProjectEntity){
            return savedProjectEntity;
        }
        return null;
    }
}
