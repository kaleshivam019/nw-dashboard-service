package com.ey.nwdashboard.model;

import java.util.List;

public class VacationRequest {
    private String userGPN;
    private List<VacationModel> vacations;

    public String getUserGPN() {
        return userGPN;
    }

    public void setUserGPN(String userGPN) {
        this.userGPN = userGPN;
    }

    public List<VacationModel> getVacations() {
        return vacations;
    }

    public void setVacations(List<VacationModel> vacations) {
        this.vacations = vacations;
    }
}
