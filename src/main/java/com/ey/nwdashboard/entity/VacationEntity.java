package com.ey.nwdashboard.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "vacation", schema = "public")
public class VacationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "vacation_id")
    private Long vacationId;

    @Column(name = "gpn")
    private String vacationUserGPN;

    @Column(name = "date")
    private LocalDate vacationDate;

    @Column(name = "planned_leave")
    private boolean isVacationPlanned;

    @Column(name = "full_day")
    private boolean isVacationFullDay;

    @Column(name = "public_holiday")
    private boolean isPublicHoliday;

    @Column(name = "created_by")
    private String vacationCreatedBy;

    @Column(name = "created_on")
    private Timestamp vacationCreatedOn;

    @Column(name = "updated_by")
    private String vacationUpdatedBy;

    @Column(name = "updated_on")
    private Timestamp vacationUpdatedOn;

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

    public LocalDate getVacationDate() {
        return vacationDate;
    }

    public void setVacationDate(LocalDate vacationDate) {
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

    public String getVacationCreatedBy() {
        return vacationCreatedBy;
    }

    public void setVacationCreatedBy(String vacationCreatedBy) {
        this.vacationCreatedBy = vacationCreatedBy;
    }

    public Timestamp getVacationCreatedOn() {
        return vacationCreatedOn;
    }

    public void setVacationCreatedOn(Timestamp vacationCreatedOn) {
        this.vacationCreatedOn = vacationCreatedOn;
    }

    public String getVacationUpdatedBy() {
        return vacationUpdatedBy;
    }

    public void setVacationUpdatedBy(String vacationUpdatedBy) {
        this.vacationUpdatedBy = vacationUpdatedBy;
    }

    public Timestamp getVacationUpdatedOn() {
        return vacationUpdatedOn;
    }

    public void setVacationUpdatedOn(Timestamp vacationUpdatedOn) {
        this.vacationUpdatedOn = vacationUpdatedOn;
    }
}
