package com.ey.nwdashboard.model;

import java.util.List;

public class FetchReportLeaveModel {
    private List<String> currentMonth;
    private List<String> nextMonth;

    public List<String> getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(List<String> currentMonth) {
        this.currentMonth = currentMonth;
    }

    public List<String> getNextMonth() {
        return nextMonth;
    }

    public void setNextMonth(List<String> nextMonth) {
        this.nextMonth = nextMonth;
    }
}
