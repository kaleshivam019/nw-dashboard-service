package com.ey.nwdashboard.model;

import java.util.List;

public class VacationResponse {
    private List<VacationModel> vacations;

    public List<VacationModel> getVacations() {
        return vacations;
    }

    public void setVacations(List<VacationModel> vacations) {
        this.vacations = vacations;
    }
}
