package com.ey.nwdashboard.model;

import java.util.List;

public class FetchReportProjectModel {
    private String projectName;
    private List<FetchReportUserModel> users;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<FetchReportUserModel> getUsers() {
        return users;
    }

    public void setUsers(List<FetchReportUserModel> users) {
        this.users = users;
    }
}
