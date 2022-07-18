package com.ey.nwdashboard.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "public_holiday", schema = "public")
public class PublicHolidayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "holiday_date")
    private LocalDate publicHolidayDate;

    @Column(name = "created_by")
    private String publicHolidayCreatedBy;

    @Column(name = "created_on")
    private Timestamp publicHolidayCreatedOn;

    @Column(name = "updated_by")
    private String publicHolidayUpdatedBy;

    @Column(name = "updated_on")
    private Timestamp publicHolidayUpdatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getPublicHolidayDate() {
        return publicHolidayDate;
    }

    public void setPublicHolidayDate(LocalDate publicHolidayDate) {
        this.publicHolidayDate = publicHolidayDate;
    }

    public String getPublicHolidayCreatedBy() {
        return publicHolidayCreatedBy;
    }

    public void setPublicHolidayCreatedBy(String publicHolidayCreatedBy) {
        this.publicHolidayCreatedBy = publicHolidayCreatedBy;
    }

    public Timestamp getPublicHolidayCreatedOn() {
        return publicHolidayCreatedOn;
    }

    public void setPublicHolidayCreatedOn(Timestamp publicHolidayCreatedOn) {
        this.publicHolidayCreatedOn = publicHolidayCreatedOn;
    }

    public String getPublicHolidayUpdatedBy() {
        return publicHolidayUpdatedBy;
    }

    public void setPublicHolidayUpdatedBy(String publicHolidayUpdatedBy) {
        this.publicHolidayUpdatedBy = publicHolidayUpdatedBy;
    }

    public Timestamp getPublicHolidayUpdatedOn() {
        return publicHolidayUpdatedOn;
    }

    public void setPublicHolidayUpdatedOn(Timestamp publicHolidayUpdatedOn) {
        this.publicHolidayUpdatedOn = publicHolidayUpdatedOn;
    }
}
