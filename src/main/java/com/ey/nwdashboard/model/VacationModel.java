package com.ey.nwdashboard.model;

public class VacationModel {
    private String vacationDate;
    private boolean isVacationPlanned;
    private boolean isVacationFullDay;
    private boolean isPublicHoliday;

    public String getVacationDate() {
        return vacationDate;
    }

    public void setVacationDate(String vacationDate) {
        this.vacationDate = vacationDate;
    }

    public boolean isVacationPlanned() {
        return isVacationPlanned;
    }

    public void setVacationPlanned(boolean vacationPlanned) {
        isVacationPlanned = vacationPlanned;
    }

    public boolean isVacationFullDay() {
        return isVacationFullDay;
    }

    public void setVacationFullDay(boolean vacationFullDay) {
        isVacationFullDay = vacationFullDay;
    }

    public boolean isPublicHoliday() {
        return isPublicHoliday;
    }

    public void setPublicHoliday(boolean publicHoliday) {
        isPublicHoliday = publicHoliday;
    }
}
