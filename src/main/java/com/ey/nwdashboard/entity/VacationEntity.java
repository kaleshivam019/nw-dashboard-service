package com.ey.nwdashboard.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "vacation", schema = "public")
public class VacationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "vacation_id")
    private Long vacationId;

    @Column(name = "gpn")
    private String vacationUserGPN;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "mm/DD/yyyy")
    @Column(name = "date")
    private Date vacationDate;

    @Column(name = "vacation_type")
    private boolean isVacationPlanned;

    @Column(name = "full_day")
    private boolean isVacationFullDay;

    @Column(name = "public_holiday")
    private boolean isPublicHoliday;

    @Column(name = "created_by")
    private String userCreatedBy;

    @Column(name = "created_on")
    private Timestamp userCreatedOn;

    @Column(name = "updated_by")
    private String userUpdatedBy;

    @Column(name = "updated_on")
    private Timestamp userUpdatedOn;

    public Long getVacationId() {
        return vacationId;
    }

    public void setVacationId(Long vacationId) {
        this.vacationId = vacationId;
    }

    public String getVacationUserGPN() {
        return vacationUserGPN;
    }

    public void setVacationUserGPN(String vacationUserGPN) {
        this.vacationUserGPN = vacationUserGPN;
    }

    public Date getVacationDate() {
        return vacationDate;
    }

    public void setVacationDate(Date vacationDate) {
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

    public String getUserCreatedBy() {
        return userCreatedBy;
    }

    public void setUserCreatedBy(String userCreatedBy) {
        this.userCreatedBy = userCreatedBy;
    }

    public Timestamp getUserCreatedOn() {
        return userCreatedOn;
    }

    public void setUserCreatedOn(Timestamp userCreatedOn) {
        this.userCreatedOn = userCreatedOn;
    }

    public String getUserUpdatedBy() {
        return userUpdatedBy;
    }

    public void setUserUpdatedBy(String userUpdatedBy) {
        this.userUpdatedBy = userUpdatedBy;
    }

    public Timestamp getUserUpdatedOn() {
        return userUpdatedOn;
    }

    public void setUserUpdatedOn(Timestamp userUpdatedOn) {
        this.userUpdatedOn = userUpdatedOn;
    }
}
