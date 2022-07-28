package com.ey.nwdashboard.model;

import java.util.List;

public class FetchReportResponse {
    private List<FetchReportProjectModel> projects;

    public List<FetchReportProjectModel> getProjects() {
        return projects;
    }

    public void setProjects(List<FetchReportProjectModel> projects) {
        this.projects = projects;
    }
}
