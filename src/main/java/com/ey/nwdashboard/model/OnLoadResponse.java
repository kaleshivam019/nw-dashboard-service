package com.ey.nwdashboard.model;

import java.util.List;

public class OnLoadResponse {
    private List<UserModel> users;
    private List<CurrentDayVacationModel> onVacationToday;
    private PublicHolidayToday publicHolidayToday;

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    public List<CurrentDayVacationModel> getOnVacationToday() {
        return onVacationToday;
    }

    public void setOnVacationToday(List<CurrentDayVacationModel> onVacationToday) {
        this.onVacationToday = onVacationToday;
    }

    public PublicHolidayToday getPublicHolidayToday() {
        return publicHolidayToday;
    }

    public void setPublicHolidayToday(PublicHolidayToday publicHolidayToday) {
        this.publicHolidayToday = publicHolidayToday;
    }
}
