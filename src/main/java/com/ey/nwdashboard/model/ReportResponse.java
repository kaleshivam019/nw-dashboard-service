package com.ey.nwdashboard.model;

public class ReportResponse {
    private String status,description;
    public ReportResponse() {
    }
    public ReportResponse(String status, String description) {
        this.status = status;
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
