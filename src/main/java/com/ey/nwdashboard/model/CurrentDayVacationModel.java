package com.ey.nwdashboard.model;

public class CurrentDayVacationModel {
    private int id;
    private String name;
    private String team;
    private String email;
    private String location;
    private boolean vacationPlanned;
    private boolean vacationFullDay;
    private boolean publicHoliday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isVacationPlanned() {
        return vacationPlanned;
    }

    public void setVacationPlanned(boolean vacationPlanned) {
        this.vacationPlanned = vacationPlanned;
    }

    public boolean isVacationFullDay() {
        return vacationFullDay;
    }

    public void setVacationFullDay(boolean vacationFullDay) {
        this.vacationFullDay = vacationFullDay;
    }

    public boolean isPublicHoliday() {
        return publicHoliday;
    }

    public void setPublicHoliday(boolean publicHoliday) {
        this.publicHoliday = publicHoliday;
    }
}
