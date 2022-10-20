package com.ey.nwdashboard.service;

import com.ey.nwdashboard.model.MessageModelResponse;
import com.ey.nwdashboard.model.ProjectModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {
    ResponseEntity<List<ProjectModel>> fetchProjects();
    ResponseEntity<MessageModelResponse> addProject(List<ProjectModel> projectModelList);
}
