package com.ey.nwdashboard.model;

import com.ey.nwdashboard.entity.*;

import java.util.List;

public class DBDataRequestResponse {
    private List<ProjectEntity> projectEntityList;
    private List<PublicHolidayEntity> publicHolidayEntityList;
    private List<TrackerEntity> trackerEntityList;
    private List<UserEntity> userEntityList;
    private List<UserRegisteredEntity> userRegisteredEntityList;
    private List<VacationEntity> vacationEntityList;

    public List<ProjectEntity> getProjectEntityList() {
        return projectEntityList;
    }

    public void setProjectEntityList(List<ProjectEntity> projectEntityList) {
        this.projectEntityList = projectEntityList;
    }

    public List<PublicHolidayEntity> getPublicHolidayEntityList() {
        return publicHolidayEntityList;
    }

    public void setPublicHolidayEntityList(List<PublicHolidayEntity> publicHolidayEntityList) {
        this.publicHolidayEntityList = publicHolidayEntityList;
    }

    public List<TrackerEntity> getTrackerEntityList() {
        return trackerEntityList;
    }

    public void setTrackerEntityList(List<TrackerEntity> trackerEntityList) {
        this.trackerEntityList = trackerEntityList;
    }

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }

    public List<UserRegisteredEntity> getUserRegisteredEntityList() {
        return userRegisteredEntityList;
    }

    public void setUserRegisteredEntityList(List<UserRegisteredEntity> userRegisteredEntityList) {
        this.userRegisteredEntityList = userRegisteredEntityList;
    }

    public List<VacationEntity> getVacationEntityList() {
        return vacationEntityList;
    }

    public void setVacationEntityList(List<VacationEntity> vacationEntityList) {
        this.vacationEntityList = vacationEntityList;
    }
}
