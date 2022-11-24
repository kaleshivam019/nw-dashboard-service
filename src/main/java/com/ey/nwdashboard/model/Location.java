package com.ey.nwdashboard.model;

import java.util.List;

public class Location {
    private String location;
    private List<Holiday> holidays;

    public Location(String location){
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }
}
