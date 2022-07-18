package com.ey.nwdashboard.model;

import java.util.List;

public class PublicHolidayRequest {
    List<PublicHolidayModel> publicHolidays;

    public List<PublicHolidayModel> getPublicHolidays() {
        return publicHolidays;
    }

    public void setPublicHolidays(List<PublicHolidayModel> publicHolidays) {
        this.publicHolidays = publicHolidays;
    }
}
