package com.ey.nwdashboard.model;

import java.util.List;

public class FetchReportResponse {
    private List<FetchReportProjectModel> projects;
    private PublicHolidays publicHolidays;

    public List<FetchReportProjectModel> getProjects() {
        return projects;
    }

    public void setProjects(List<FetchReportProjectModel> projects) {
        this.projects = projects;
    }

    public PublicHolidays getPublicHolidays() {
        return publicHolidays;
    }

    public void setPublicHolidays(PublicHolidays publicHolidays) {
        this.publicHolidays = publicHolidays;
    }
}
