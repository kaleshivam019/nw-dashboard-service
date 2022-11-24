package com.ey.nwdashboard.model;

import java.util.List;

public class PublicHolidays {
    private List<String> months;
    private List<Location> locations;

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
