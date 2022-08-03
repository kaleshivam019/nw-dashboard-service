package com.ey.nwdashboard.model;

public class ProjectModel {

    private String projectName;

    private String leadName;

    private String line;

    public ProjectModel(String projectName, String leadName, String line) {
        this.projectName = projectName;
        this.leadName = leadName;
        this.line = line;
    }

    public ProjectModel() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
