package com.ey.nwdashboard.service;

import com.ey.nwdashboard.model.ProjectModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FetchProjectService {
    ResponseEntity<List<ProjectModel>> fetchProjects();
}
