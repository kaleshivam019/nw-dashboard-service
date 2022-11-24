package com.ey.nwdashboard.model;

import java.util.List;

public class Holiday {
    private String month;
    private List<String> dates;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
}
